console.log("Promise exam");

function task(fullfill, reject) {
    console.log("task 실행");
    setTimeout(function () {
        console.log("task 실행 끝");
        // fullfill("실행 결과 값");
        rejected("Error message");
    },1000)
}

function fullfilled(result) {
    console.log("fullfilled 실행 > ", result);
}

function rejected(err) {
    console.log("rejected 실행 > ", err);
}

new Promise(task).then(fullfilled, rejected);