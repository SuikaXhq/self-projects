'''
Created on 2019年1月14日

@author: SuikaXhq
'''
import re

if __name__ == '__main__':
    url = 'https://i.pximg.net/img-original/img/2019/01/12/00/03/19/72611804_p0.jpg'
    name = '「暮透」的作品 - pixiv'
    time_pattern = re.compile('^https://i.pximg.net/img-original/img/(.*)/[0-9]+_p0\\..*$')
    postfix_pattern = re.compile('^https://i.pximg.net/img-original/img/.*_p0\\.(.*)$')
    author_pattern = re.compile('^「(.*)」的作品')
    print(author_pattern.match(name).group(1))