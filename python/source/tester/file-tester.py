'''
Created on 2019年1月7日

@author: SuikaXhq
'''

if __name__ == '__main__':
    with open('test.txt', mode='r+') as f:
        if f.readline() != '123456':
            f.seek(0)
            f.truncate()
            f.write('123456')
            f.close()
            print('Changed.')
        else:
            f.close()
            print('Non-change.')