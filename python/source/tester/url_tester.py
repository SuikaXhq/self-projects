'''
Created on 2019年1月7日

@author: SuikaXhq
'''
from bs4 import BeautifulSoup as bs
import urllib3
import os
import operator as op
import json

if __name__ == '__main__':
    default_header = {'User-Agent': 'Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0',
                      'Referer': 'https://www.pixiv.net/member_illust.php?mode=medium&illust_id=72611804',
                      'Cookie': r'first_visit_datetime_pc=2018-12-06+21%3A06%3A48; p_ab_id=3; p_ab_id_2=5; p_ab_d_id=232907776; yuid_b=EkJZEpg; __utma=235335808.1296020338.1544537940.1547403936.1547451733.12; __utmz=235335808.1547302162.8.3.utmcsr=t.co|utmccn=(referral)|utmcmd=referral|utmcct=/AP6GkKUj7R; __utmv=235335808.|2=login%20ever=yes=1^3=plan=normal=1^5=gender=male=1^6=user_id=13088661=1^9=p_ab_id=3=1^10=p_ab_id_2=5=1^11=lang=zh=1; _ga=GA1.2.1296020338.1544537940; privacy_policy_agreement=1; c_type=99; a_type=0; b_type=1; module_orders_mypage=%5B%7B%22name%22%3A%22sketch_live%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22tag_follow%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22recommended_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22everyone_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22following_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22mypixiv_new_illusts%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22spotlight%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22fanbox%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22featured_tags%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22contests%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22user_events%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22sensei_courses%22%2C%22visible%22%3Atrue%7D%2C%7B%22name%22%3A%22booth_follow_items%22%2C%22visible%22%3Atrue%7D%5D; login_ever=yes; tag_view_ranking=iFcW6hPGPU~EGefOqA6KB~qvqXJkzT2e~0xsDLqCEW6~RTJMXD26Ak~KN7uxuR89w~tj5XLlVbyo~nRnn8VBmkN~QviSTvyfUE~FH69TLSzdM~ASmLfCftwy~W8b5FozT7j~q3eUobDMJW~aB2SE07Kn8~xr1wAkJUyp~xA0S-y1JwO~d-u0duThlB~azESOjmQSV~05tD6f663z~mLrrjwTHBm~dqqWNpq7ul~UgLGWGysi-~Is0SiXyaWb~Hjx7wJwsUT~6akAxELRtT~v3f3nUY-vS~wyZOlBKxtg~H6lk0ujEdQ~QRGsAC7TD3~Jg_BKFcFMF~lofyj07fO-~at5kYG0Mvu~6s8EWwIFtr~udkRh_mjvd~tgP8r-gOe_~8jKDxD8X_a~6JqkRv099r~B9WjdeT8q-~n2tofz1Xl7~xXhWS_7AGn~emNf5ejWtw~qWFESUmfEs~phyAxUXrUB~rgxOsa3XtV~VN7cgWyMmg~oaDlG2PaXD~BSkdEJ73Ii~7WfWkHyQ76~27KGuaCVQE~WcTW9TCOx9~mSipZ8R7EW~hHXjRsPIa6~YqQOHZiATg~9s62wUfVkX~UX647z2Emo~s9tsK0hQTn~Su1mNmmP-f~MsF32uM-vh~CLR9k9dHAQ~bw7yUHoY9E~WI561SX4pn~L2o1huceHX~Ie2c51_4Sp~7ZQ3qjcL3U~LVSDGaCAdn~NcZQBKC8XT~HhUTeE89Ow~oP4EhchglV~FpwLUmX5rH~0GyqV8JLK3~zIv0cf5VVk~q303ip6Ui5~hzLsBUtKYm~SQBqboVSX_~YYXnZO5Qu9~OT4SuGenFI~e0_ymoIxq3~nRp2ZLPLbj~nQRrj5c6w_~ZSvbPeZmjk~E1aHB32Zdc~r1_DY37ujr~LSG3qSZIDS~i0KTH8ScbW~kQXtl09mmb~jk9IzfjZ6n~WlKkwEuUi0~GoX_IRh0Hp~DosDk0jWon~xEkHostqyh~x5kk8MCUrJ~cKKaLdcqk_~dmFVI1740a~tGJ-oLmWrs~PWDp4KAibH~PZhec8OFP9~2QTW_H5tVX~yv-MdmoUJ0~blN5RWq7CX~CrFcrMFJzz; limited_ads=%7B%22responsive%22%3A%22%22%7D; categorized_tags=CADCYLsad0~IVwLyT8B6k; PHPSESSID=13088661_30ae3399ea4a6bfdf2fdcb3973b49152; _gid=GA1.2.1170770245.1547303270; device_token=c0344814b38e76ddf496eb1c406dbc47; is_sensei_service_user=1; __utmc=235335808; __utmb=235335808.14.9.1547452305406; __utmt=1'
                     }
    url = 'https://i.pximg.net/img-original/img/2019/01/12/00/03/19/72611804_p0.jpg'
    urllib3.disable_warnings()
    http = urllib3.PoolManager()
    proxy = urllib3.ProxyManager('http://127.0.0.1:1080/', timeout=2,headers=default_header)
    request = proxy.request('GET', url, headers={})
    print(request.data)