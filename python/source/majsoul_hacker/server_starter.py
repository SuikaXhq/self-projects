'''
Created on 2019年1月7日

@author: SuikaXhq
'''
import version_checker as vc
import http.server

if __name__ == '__main__':
    vc.check()
    handler = http.server.SimpleHTTPRequestHandler
    with http.server.HTTPServer(('127.0.0.1', 8000), handler) as httpse:
        print('Serving HTTP at Port 8000...')
        httpse.serve_forever()