'''
Created on 2019年1月11日

@author: SuikaXhq
'''
import MtU_retryer
import re
import os
import operator as op
        
def main():
    print('Check for update...')
    host_soup = MtU_retryer.get_html_soup(MtU_retryer.target_html_url)
    print('Got host page.')
    
    host_list = host_soup.find_all('li', class_='list_item no_trim')
    latest_name = host_list[0].find('img')['alt']
    latest_name = re.sub('[\\\/\:\*\?\"\<\>\|]', '', latest_name)
    
    #寻找最新图名
    old_list = os.listdir('MtU/images')
    old_name = ''
    for name1 in old_list:
        flag = True
        for name2 in old_list:
            if op.lt(name1, name2):
                flag = False
                break
        if not flag:
            continue
        
        old_name = name1
        
    if latest_name in old_name:
        print('None updates. Program ended.')
        return
    
    print('Update found. Spidering...')
    
    #下载更新
    temp_name = latest_name
    temp_soup = host_soup
    temp_list = host_list
    page = 1
    counter = 0
    while temp_name not in old_name:
        url = MtU_retryer.host_url + temp_list[counter].a['href']
        MtU_retryer.download(url)
        
        counter += 1
        if counter > 39:#换页
            page += 1
            next_url = 'http://seiga.nicovideo.jp/user/illust/12128756?page={}&target=illust_all'.format(page)
            temp_soup = MtU_retryer.get_html_soup(next_url)
            temp_list = temp_soup.find_all('li', class_='list_item no_trim')
            counter = 0
            
        temp_name = temp_list[counter].find('img')['alt']
        
    print('Update finished. Program ended.')

if __name__ == '__main__':
    main()