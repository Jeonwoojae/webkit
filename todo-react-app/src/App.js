import { render } from "react-dom";
import Todo from "./Todo";
import React, { useEffect, useState } from "react";
import { Paper, List, Container } from "@material-ui/core";
import AddTodo from "./AddTodo";
import call from "./service/ApiService";

function App() {
  // 매개변수 props 생성자
  const [items, setItems] = useState([]);
  const [serverCall, setServerCall] = useState(false);
  // item에 item.id, item.title, item.done 매개변수 이름과 값 할당

  // (1) add 함수 추가
  const addItem = (item) => {
    console.log("add request", item);
    call("/todo", "POST", item).then((response) => {
      console.log("post success");
      setServerCall(!serverCall);
    });
  };

  // (3) delete 함수 추가
  const deleteItem = (item) => {
    console.log("delete request", item);
    call("/todo", "DELETE", item).then((response) => {
      console.log("delete success");
      setServerCall(!serverCall);
    });
  };

  const update = (item) => {
    console.log("update request", item);
    call("/todo", "PUT", item).then((response) => {
      console.log("update success");
      setServerCall(!serverCall);
    });
  };

  // 처음 실행 시 기존에 todo 불러오는 작업 필요
  useEffect(() => {
    return () => {
      call("/todo", "GET", null).then((response) => {
        setItems(response.data); // update State
        console.log(response.data);
        console.log("get todo from server");
      });
    };
  }, [serverCall]);


  // todoItems에 length가 0보다 크다면 true
  // 삼항연산자도 사용 가능하다
  var todoItems = items.length > 0 && (
    // 자바스크립트가 제공하는 map 함수를 이용해서 배열을 반복해 <Todo/> 컴포넌트를 여러 개 생성한다.
    <Paper style={{ margin: 16 }}>
      <List>
        {items.map((item, idx) => (
          <Todo item={item} key={item.id} delete={deleteItem} update={update} />
        ))}
      </List>
    </Paper>
  );

  // 생성된 컴포넌트 JSX를 리턴한다.
  // (2) add 함수 연결
  return (
    <div className="App">
      <Container maxWidth="md">
        <AddTodo add={addItem} />
        <div className="TodoList">{todoItems}</div>
      </Container>
    </div>
  );
}

export default App;
