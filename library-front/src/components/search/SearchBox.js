import { Button, Input } from "@mui/material";
import React, { useState } from "react";
import Dropdown from "./Dropdown";

function SearchBox() {
    const [text, setText] = useState('');

    const onChange = (e) => {
        setText(e.target.value);
    }

  return (
    <div className="Box">
      <Dropdown />
      <Input onChange={onChange} placeholder="Placeholder"/>
      <Button className="searchbox-button" variant="outlined" size="medium">
        검색
      </Button>
      <Button className="searchbox-button" variant="outlined" size="medium">
        추가
      </Button>
    </div>
  );
}

export default SearchBox;
