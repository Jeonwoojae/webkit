export const menuItems = [
    {
      title: 'Home',
      url: '/',
    },
    {
      title: '국내 만화',
      url: '/book/search?nation=대한민국',
      submenu: [
        {
          title: '순정만화',
          url: '/book/search?nation=대한민국&genre=순정만화',
        },
        {
          title: '소년만화',
          url: '/book/search?nation=대한민국&genre=소년만화',
        },
        {
          title: '성인만화',
          url: '/book/search?nation=대한민국&genre=성인만화',
        },
        {
          title: '기획도서',
          url: '/book/search?nation=대한민국&genre=기획도서',
        },
        {
          title: '만화잡지',
          url: '/book/search?nation=대한민국&genre=만화잡지',
        },
      ],
    },
    {
      title: '일본 만화',
      url: '/book/search?nation=일본&genre=순정만화',
      submenu: [
        {
          title: '순정만화',
          url: '/book/search?nation=일본&genre=순정만화',
        },
        {
          title: '소년만화',
          url: '/book/search?nation=일본&genre=소년만화',
        },
        {
          title: '성인만화',
          url: '/book/search?nation=일본&genre=성인만화',
        },
        {
          title: '기획도서',
          url: '/book/search?nation=일본&genre=기획도서',
        },
      ],
    },
    {
      title: '미국 만화',
      url: '/book/search?nation=미국',
      submenu: [
        {
          title: 'DC 코믹스마블',
          url: '/book/search?nation=미국&genre=DC 코믹스마블',
        },
        {
          title: '코믹스리터러리',
          url: '/book/search?nation=미국&genre=코믹스리터러리',
        },
        {
          title: '그래픽노블',
          url: '/book/search?nation=미국&genre=그래픽노블',
        },
      ],
    },
  ];