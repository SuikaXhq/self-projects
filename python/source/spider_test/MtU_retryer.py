'''
Created on 2019年1月11日

@author: SuikaXhq
'''
import urllib3 as u
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
    
def download(url):
    try:
        sub_soup = bs(proxy.request('GET', url).data, 'lxml')#单张图片的soup
           
        image_time = sub_soup.find(class_='created').string[:-6]
        image_name = sub_soup.find('h1', class_='title').string
        image_name = re.sub('[\\\/\:\*\?\"\<\>\|]', '', image_name)#去除非法名字
        file_path = 'MtU/images/[MtU][' + image_time + ']' + image_name + '.jpg' #合成文件路径
        
        image_url_out = host_url + sub_soup.find(id='illust_link')['href']#获取图片表地址
        image_url_in = proxy.request('GET', image_url_out, redirect=False).get_redirect_location()#里地址
        image_soup = bs(proxy.request('GET', image_url_in).data, 'lxml')
        image_file_url = sub_url + image_soup.find(class_='illust_view_big')['data-src']
        with open(file_path, mode='wb') as j:
            j.write(proxy.request('GET', image_file_url).data)#保存jpg
            print('Created', file_path)
        sleep(1)
        return True
    except u.exceptions.MaxRetryError as err:
        print(err)
        return False
    
def get_html_soup(url):
    return bs(proxy.request('GET', url).data, 'lxml')
    
def main():
    u.disable_warnings()
    retry_list = []
    with open('MtU/retry.txt', mode='r') as r:
        oldlist = r.readlines()
        
    for url in oldlist:
        url.strip()
        if not download(url):
            retry_list.append(url + '\n')
    
    with open('MtU/retry.txt', mode='w') as r:
        r.writelines(retry_list)
        
if __name__ == '__main__':
    main()