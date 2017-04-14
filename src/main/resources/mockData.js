function makeArray(length) { var a = []; for(var i = 0; i < length; i++) {a.push(null)} return a;}
var listData = makeArray(100)
    .map(function (undef, i) {
                        return {
                          title: 'Product title ' + i,
                          img: 'https://img.alicdn.com/tps/TB13keMLXXXXXbmXVXXXXXXXXXX-900-500.jpg',
                          href: 'https://github.com',
                          price: 20
                        };
                      });

var bannerData = makeArray(6)
                                       .map(function(undef, i) {
                                         return {
                                           title: 'banner ' + i,
                                           img: 'https://img.alicdn.com/tps/TB13keMLXXXXXbmXVXXXXXXXXXX-900-500.jpg'
                                         };
                                       })
