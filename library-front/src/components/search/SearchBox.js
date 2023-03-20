import { Button, Input } from "@mui/material";
import React, { useState } from "react";
import { Link } from "react-router-dom";
import Dropdown from "./Dropdown";

function SearchBox() {
  const [text, setText] = useState("");
  const [condition, setCondition] = useState("");

  const onChange = (e) => {
    setText(e.target.value);
  };

  return (
    <div className="Box">
      <Dropdown setCondition={setCondition} />
      <Input onChange={onChange} placeholder="Placeholder" />
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
  );
}

export default SearchBox;
