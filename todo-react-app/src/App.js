import { render } from "react-dom";
import Todo from "./Todo";
import React, { useState } from "react";
import { Paper, List, Container } from "@material-ui/core";
import AddTodo from "./AddTodo";

function App() {
  // 매개변수 props 생성자
  const [items, setItems] = useState([
    { id: "ID-0", title: "Todo 1", done: false },
    { id: "ID-1", title: "Todo 2", done: false },
  ]);
  // item에 item.id, item.title, item.done 매개변수 이름과 값 할당

  // (1) add 함수 추가
  const addItem = (item) => {
    console.log(item);
    item.id = "ID-" + items.length; // key를 위한 id 추가
    item.done = false;
    const thisItems = [...items, item];
    setItems(thisItems); // update State
    console.log("items:", thisItems);
  };

  // (3) delete 함수 추가
  const deleteItem = (item) => {
    const thisItems = items;
    const newItems = thisItems.filter((e) => e.id !== item.id);
    setItems(newItems, () => {
      // 디버깅 콜백
      console.log("Update Items : ", items);
    });
  };

  // todoItems에 length가 0보다 크다면 true
  // 삼항연산자도 사용 가능하다
  var todoItems = items.length > 0 && (
    // 자바스크립트가 제공하는 map 함수를 이용해서 배열을 반복해 <Todo/> 컴포넌트를 여러 개 생성한다.
    <Paper style={{ margin: 16 }}>
      <List>
        {items.map((item, idx) => (
          <Todo item={item} key={item.id} delete={deleteItem} />
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
