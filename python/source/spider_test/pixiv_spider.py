'''
Created on 2019年1月12日

@author: SuikaXhq
'''
import urllib3 as u
import os
import re
from time import sleep
from url_manager import URLManager
import configparser
import json
import operator as op

host_url = 'https://www.pixiv.net'
time_pattern = re.compile('^https://i.pximg.net/img-original/img/(.*)/[0-9]+_p0\\..*$')
postfix_pattern = re.compile('^https://i.pximg.net/img-original/img/.*_p0\\.(.*)$')
author_pattern = re.compile('^「(.*)」的作品')

def get_cookie():
    with open('Pixiv/cookie.txt', mode='r') as c:
        return c.readline().strip()

def download(manager, pid, save_path='', time_limit=''):
    try:
        image_url_out = 'https://www.pixiv.net/ajax/illust/{}'.format(pid) #图片表地址，请求后得到json
        image_url_json = json.loads(manager.request('GET', image_url_out).data)
        image_url_in = image_url_json['body']['urls']['original']#真实图片地址
        author_name = image_url_json['body']['userName']
        illust_name = image_url_json['body']['illustTitle']
        postfix = postfix_pattern.match(image_url_in).group(1)#图片后缀
        
        #判断是否超过设定的时间限制
        illust_time = time_pattern.match(image_url_in).group(1).replace('/', '.')
        if op.lt(illust_time, time_limit):
            print('Time limit reached.')
            return False
        
        image_name = '[{author}][{time}][{pid}]{name}.{fix}'.format(author=author_name, time=illust_time, pid=pid, name=illust_name, fix=postfix)#合成图片名
        image_path = save_path + image_name
        header_referer = {'Referer': 'https://www.pixiv.net/member_illust.php?mode=medium&illust_id={}'.format(pid)}
        
        #保存文件
        with open(image_path, mode='wb') as i:
            i.write(manager.request('GET', image_url_in, headers=header_referer).data)
            print('Created', image_path)
        
        return True
    except u.exceptions.MaxRetryError as err:
        with open('Pixiv/retry.txt', mode='a') as r:
            r.write(pid)
        print('Time out. Retry this pid({}) later.'.format(pid))
        return True

def spider(manager, author_id, current_illust, time_limit=''):
    print('Starting spidering...')
    
    #获取作者名
    author_soup = manager.get_html_soup('https://www.pixiv.net/member.php?id={}'.format(author_id))
    author_name = author_pattern.match(author_soup.head.title.string).group(1)
    print('Author name: ', author_name)
    #创建文件夹
    if author_name not in os.listdir('Pixiv/'):
        os.mkdir('Pixiv/{}'.format(author_name))
        print('Created Pixiv/{}/'.format(author_name))
    
    #获取作者作品列表
    illusts_json = json.loads(manager.request('GET', 'https://www.pixiv.net/ajax/user/{}/profile/all'.format(author_id)).data)
    illusts_list = list(illusts_json['body']['illusts'].keys())#pid列表
    
    if current_illust=='':
        start_index = 0
        print('Start from latest illust.')
    else:
        start_index = illusts_list.index(current_illust) + 1
        print('Start from illust {}'.format(illusts_list[start_index]))
        
    #遍及整个列表（从current_illust开始）
    for index in range(start_index, len(illusts_list)):
        if download(manager, illusts_list[index], save_path='Pixiv/{}/'.format(author_name), time_limit=time_limit):
            current_illust = illusts_list[index]
            sleep(1)
        else:
            break
    
    print('Spider done. Last downloaded illust {}'.format(current_illust))
    return current_illust

def main():
    u.disable_warnings()
    cf = configparser.ConfigParser()
    cf.read('Pixiv/config.ini')
    print('Press enter to use default config.')
    author_id = input('Illustrator ID:')
    
    if author_id=='':
        author_id = cf.get('Pixiv', 'author_id')
        time_limit = cf.get('Pixiv', 'time_limit')
        current_illust = cf.get('Pixiv', 'current_illust')
    else:
        cf.set('Pixiv', 'author_id', id)
        time_limit = input('Time limit:')
        cf.set('Pixiv', 'time_limit', time_limit)
        current_illust = ''
        
    m = URLManager(cookie=get_cookie(), proxy_url='http://127.0.0.1:1080/')
    cf.set('Pixiv', 'current_illust', spider(m, author_id, current_illust, time_limit))#开始爬图
    
    print('Program ended.')
    
    
if __name__ == '__main__':
    main()