var http = require('http');


const server = http.createServer(function(req,res){
    res.end("<h1>Hello world</h1>")
});

Server.listen(3000, function() {
    console.log("서버가 실행중");
});

http.createServer(function(req,res) {
    res.writeHead(200, {'Content-Type':'text/html'});
    res.end('Hello World');
}).listen(3000);


// console.log("Hello node.js world");
// for(var i=0;i<10;i++){
//     console.log(i);
// }