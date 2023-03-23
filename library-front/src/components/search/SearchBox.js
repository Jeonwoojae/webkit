import { Button, Input } from "@mui/material";
import React, { useState } from "react";
import { Link } from "react-router-dom";
import Dropdown from "./Dropdown";
import "./SearchBox.css"

function SearchBox() {
  const [text, setText] = useState("");
  const [condition, setCondition] = useState("");

  const onChange = (e) => {
    setText(e.target.value);
  };

  return (
    <div align="right" className="box">
      <Dropdown className="search-dropdown" setCondition={setCondition} />
      <div className="input-box">
        <input className="input-form" maxLength="30" onChange={onChange} placeholder="검색할 내용을 입력하세요" />
        <Link to={`/book/search?q=${text}&c=${condition}`}>
          <Button className="searchbox-button" variant="outlined" size="medium">
            검색
          </Button>
        </Link>
        <Link to={"/book"}>
          <Button className="searchbox-button" variant="outlined" size="medium">
            추가
          </Button>
        </Link>
      </div>
    </div>
  );
}

export default SearchBox;
