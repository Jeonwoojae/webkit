import { render } from "react-dom";
import Todo from "./Todo";
import React, { useEffect, useState } from "react";
import {
  Paper,
  List,
  Container,
  AppBar,
  Toolbar,
  Grid,
  Typography,
  Button,
} from "@material-ui/core";
import AddTodo from "./AddTodo";
import call, { signout } from "./service/ApiService";

function App() {
  // 매개변수 props 생성자
  const [items, setItems] = useState([]);
  const [serverCall, setServerCall] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

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

  const componentDidmount = () => {
    call("/todo", "GET", null).then((response) => {
      setItems(response.data); // update State
      setIsLoading(false);
      console.log(response.data);
      console.log("get todo from server");
    });
  }

  useEffect(() => {
    componentDidmount();
  }, [serverCall]);

  // navigationBar
  var navigationBar = (
    <AppBar position="static">
      <Toolbar>
        <Grid justifyContent="space-between" container>
          <Grid item>
            <Typography variant="h6">오늘의 할일</Typography>
          </Grid>
          <Grid item>
            <Button color="inherit" onClick={signout}>
              logout
            </Button>
          </Grid>
        </Grid>
      </Toolbar>
    </AppBar>
  );

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
    <>
      {isLoading ? (
        // 로딩 중일 때
        <h1>로딩중...</h1>
      ) : (
        // 로딩 중이 아닐 때
        <div className="App">
          {navigationBar}
          <Container maxWidth="md">
            <AddTodo add={addItem} />
            <div className="TodoList">{todoItems}</div>
          </Container>
        </div>
      )}
    </>
  );
}

export default App;
