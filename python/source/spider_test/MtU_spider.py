'''
Created on 2019年1月10日

Target: MtU

@author: SuikaXhq
'''
import urllib3 as u
import os
import re
from bs4 import BeautifulSoup as bs
from time import sleep

host_url = 'http://seiga.nicovideo.jp'
sub_url = 'https://lohas.nicoseiga.jp'
target_html_url = 'http://seiga.nicovideo.jp/user/illust/12128756?target=illust_all'

def get_cookie():
    with open('MtU/cookie.txt', mode='r') as c:
        return c.readline().strip()

default_cookie = 'nicosid=1546878365.834620946; _ga=GA1.2.1048223607.1546878369; lang=ja-jp; area=JP; __ah_i=a%3A4%3A%7Bi%3A8739083%3Bi%3A1547144710%3Bi%3A8688890%3Bi%3A1546878410%3Bi%3A8665777%3Bi%3A1546878413%3Bi%3A8698621%3Bi%3A1547110770%3B%7D; skip_fetish_warning=1; target=illust; _gid=GA1.2.643794102.1547110328; nicorepo_filter=all; user_session=user_session_59809761_0d11db78753828488e1099a96e77cbb630bc2331b0db541bb429b4e0e581c85a; _gat_NicoGoogleTagManager=1'
default_header = {'User-Agent': 'Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0',
                  'Cookie': get_cookie()}
proxy = u.ProxyManager('http://127.0.0.1:1080/', headers=default_header, timeout=2)#使用ss代理    

def download(soup):
    retry_list = []#重试列表
    
    for tag in soup.find_all('li', class_='list_item no_trim'):#遍及单页上的所有图片
        try:
            image_url = host_url + tag.a['href']
            sub_soup = bs(proxy.request('GET', image_url).data, 'lxml')#单张图片的soup
            
            image_time = sub_soup.find(class_='created').string[:-6]
            image_name = sub_soup.find('h1', class_='title').string
            image_name = re.sub('[\\\/\:\*\?\"\<\>\|]', '', image_name)#去除非法名字
            file_path = 'MtU/images/[MtU][' + image_time + ']' + image_name + '.jpg' #合成文件路径
            if os.path.exists(file_path):
                print('Exists', file_path)
                continue
            
            image_url_out = host_url + sub_soup.find(id='illust_link')['href']#获取图片表地址
            image_url_in = proxy.request('GET', image_url_out, redirect=False).get_redirect_location()#里地址
            image_soup = bs(proxy.request('GET', image_url_in).data, 'lxml')
            image_file_url = sub_url + image_soup.find(class_='illust_view_big')['data-src']
            with open(file_path, mode='wb') as j:
                j.write(proxy.request('GET', image_file_url).data)#保存jpg
                print('Created', file_path)
            sleep(1)
        except u.exceptions.MaxRetryError as err:
            print(err)
            retry_list.append(err.url + '\n')
            continue
    
    with open('MtU/retry.txt', mode='a') as r:
        r.writelines(retry_list)
        
    
def write_html(page):
    next_url = 'http://seiga.nicovideo.jp/user/illust/12128756?page={}&target=illust_all'.format(page)
    next_html = proxy.request('GET', next_url)
    with open('MtU/{}.html'.format(page), mode='wb+') as h:
        h.write(next_html.data)
        print('Created MtU/{}.html'.format(page))
    
    return open('MtU/{}.html'.format(page), mode='rb')

def main():
    u.disable_warnings()
    target_html = proxy.request('GET', target_html_url)
    print('Starting spiding...')
    if not os.path.exists('MtU'):
        os.mkdir('MtU')
        print('Created folder MtU.')
    
    with open('MtU/host.html', mode='wb+') as h:
        h.write(target_html.data)
        print('Created MtU/host.html.')
        
    print('Starting analyzing...')
    
    #下载到最后一页
    temp_html_soup = bs(open('MtU/host.html', mode='rb'), 'lxml')
    page = 1
    while temp_html_soup.find_all('li', class_='next_disabled') == []:
        download(temp_html_soup)
        
        page += 1
        temp_html_soup = bs(write_html(page), 'lxml')
        
    else:
        download(temp_html_soup)
        print('Download finished.')
    
    print('Program ended.')
    
if __name__ == '__main__':
    main()