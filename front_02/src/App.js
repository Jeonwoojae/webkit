import React, { useEffect, useState } from "react";
import "./App.css";

function ItemRow({ item, removeItem, updateItem }) { // props로 받을수도 있음
  const [mode, setMode] = useState(false); // 수정모드인지
  const [title, setTitle] = useState(item.title); // 할일 내용
  return (
    <li>
      <p>
        <input
          checked={item.done ? "checked" : ""} // 저장된 item이 done인지에 따라 미리 체크
          type="checkbox"
          onChange={(e) => {
            // 체크박스가 변하면 변하는 상태를 item의 done에 저장하고 
            item.done = e.target.checked;

            // 기존에 있던 배열에 저장
            updateItem(item);
          }}
        />
        <input
        // 할일 표시
          value={title}
          // 수정 가능 시 변경내용 저장
          onChange={(e) => {
            setTitle(e.target.value);
          }}
          className={item.done ? "done" : "not-done"}
          type="text"
          disabled={mode ? "" : "disabled"}
        />
        <button
          onClick={(e) => {
            removeItem(item.no);
          }}
        >
          삭제
        </button>
        <button
        // 모드 변경(수정기능)
          onClick={(e) => {
            setMode(!mode);
            if (mode) {
              item.title = title;
              updateItem(item);
            } else {
            }
          }}
        >
          {mode ? "수정완료" : "수정"}
        </button>
      </p>
    </li>
  );
}

function InputItem({ appendItem }) {
  // input의 value로 사용 할 경우 초기값 필수.
  const [newWork, setNewWork] = useState("");
  return (
    <div>
      할일 :
      <input
        type="text"
        value={newWork}
        onChange={(e) => {
          setNewWork(e.target.value);
        }}
        onKeyDown={(e) => {
          if (e.key === "Enter") {
            appendItem(newWork);
            setNewWork("");
          }
        }}
      />
      <button
        onClick={(e) => {
          appendItem(newWork);
          setNewWork("");
        }}
      >
        추가
      </button>
    </div>
  );
}

// Redux를 이용하면 해결된다.
function TodoList({ todoList, removeItem, updateItem }) {
  return (
    <div>
      <ol>
        {todoList.map((item, idx) => {
          return (
            <ItemRow
              key={item.no}
              item={item}
              removeItem={removeItem}
              updateItem={updateItem}
            />
          );
        })}
      </ol>
    </div>
  );
}

function App(props) {
  const [todoList, setTodoList] = useState([]);
  const [noCount, setNoCount] = useState(1); // 카운트 갯수는 1부터 시작

  useEffect(() => {
    // localStorage에 데이터가 있으면 로드한다.
    // 저장은 문자열로 한다.
    console.log(">>>>> useEffect ...");
    const localStorageData = localStorage.getItem("todoListData");
    if (localStorageData) {
      let objData = JSON.parse(localStorageData);
      setTodoList(objData.todoList);
      setNoCount(objData.noCount);
      console.log(">>>>> data load 완료");
      console.dir(objData);
    }
  }, []);

  function saveLocalStorage(newList, noCnt) {
    localStorage.setItem(
      "todoListData",
      JSON.stringify({ todoList: newList, noCount: noCnt }) // 저장은 문자열로 저장
    );
    console.log(">>>>> localStorage에 저장 완료");
  }

  function appendItem(newItem) {
    let newList = [...todoList, { no: noCount, title: newItem, done: false }];
    let noCnt = noCount + 1;
    setTodoList(newList);
    setNoCount(noCnt);
    saveLocalStorage(newList, noCnt);
  }
  function removeItem(no) {
    // 선택한 번호의 item을 제외하고 반환하여 저장
    var newList = todoList.filter((item, idx) => {
      return item.no != no;
    });
    setTodoList(newList);
    saveLocalStorage(newList, noCount);
  }

  function updateItem(item) {
    // 수정한 item의 번호를 가지고 찾아서 수정후 저장
    console.dir("updateItem: " + JSON.stringify(item)) ;
    const idx = todoList.findIndex((todo, idx) => {
      return todo.no === item.no;
    });
    todoList[idx] = item;
    const newList = [...todoList];
    setTodoList(newList);
    saveLocalStorage(newList, noCount);
  }

  return (
    <>
      <h1>Todo List</h1>
      <InputItem appendItem={appendItem} />
      <hr />
      <TodoList
        todoList={todoList}
        removeItem={removeItem}
        updateItem={updateItem}
      />
    </>
  );
}

export default App;