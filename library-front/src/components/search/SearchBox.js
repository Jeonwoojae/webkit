import { Button, Input } from "@mui/material";
import React, { useState } from "react";
import { Link } from "react-router-dom";
import Dropdown from "./Dropdown";
import "./SearchBox.css"

function SearchBox(props) {
  const [text, setText] = useState("");
  const [condition, setCondition] = useState("");

  const searchUrl = props.nation==null&&props.genre==null ? `/book/search?q=${text}&c=${condition}` : props.genre==null ? `/book/search?nation=${props.nation}&q=${text}&c=${condition}` : `/book/search?nation=${props.nation}&genre=${props.genre}&q=${text}&c=${condition}`;

  const onChange = (e) => {
    setText(e.target.value);
  };

  return (
    <div align="right" className="box">
      <Dropdown className="search-dropdown" setCondition={setCondition} />
      <div className="input-box">
        <input className="input-form" maxLength="30" onChange={onChange} placeholder="검색할 내용을 입력하세요" />
        <Link to={searchUrl}>
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
