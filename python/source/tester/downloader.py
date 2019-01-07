'''
Created on 2019年1月7日

@author: SuikaXhq
'''
import urllib3 as u
import json

def main():
    with open('version.json', mode='r') as j:
        data = json.load(j)
    print(data['code'])

if __name__ == '__main__':
    main()
    
