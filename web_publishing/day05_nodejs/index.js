const { query } = require('express');
const express = require('express');
const app = express();

// public을 외부에서 접속 할 수 있도록 static 설정 하기.
app.use(express.static('public'));

const carList = []
let cnt = 1;

app.get('/car/input', function(req,res) {
    var name = req.query.name;
    var price = req.query.price;
    var company = req.query.company;
    var year = req.query.year;

    console.log(name, price, company, year);

    res.end(`${name},${price},${company},${year}`);

    // console.log(query);
    // // res.end() 문자열 사용
    // // res.send() object 사용
    // res.send(req.query);

    req.query.no = cnt++;
    carList.push(req.query);

    res.send(carList);
});

app.listen(3000, function() {
    console.log("노드js 서버 실행 중 : 3000");
});

