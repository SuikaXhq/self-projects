'''
Created on 2019年1月7日

@author: SuikaXhq
'''
import json
import urllib3

version_url = r'majsoul.union-game.com/0/version.json'

def check():
    print('Checking version...')
    urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)
    global version_url
    http = urllib3.PoolManager()
    version_json_response = http.request('GET', version_url)
    version_dict = json.loads(version_json_response.data)
    with open('version.txt', mode='r+') as f:
        old_version = f.readline()
        new_version = version_dict['version']
        print('Current server js version is', old_version)
        print('Current majsoul version is', new_version)
        if old_version != new_version:
            print('Version changed. Updating code.js...')
            f.seek(0)
            f.truncate()
            f.write(version_dict['version'])
            repatch(version_dict['code'])
        else:
            print('Version unchanged. Staring server...')
        
def repatch(code_path):
    print('Repatching...')
    code_url = r'majsoul.union-game.com/0/' + code_path
    http = urllib3.PoolManager()
    code_response = http.request('GET', code_url)
    with open('code.patched.js', mode='wb') as c:
        c.write(code_response.data)
        with open('patch.js', mode='r') as p:
            c.write(p.read().encode('utf-8'))
    print('Repatching done.')