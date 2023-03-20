import { FormControl, InputLabel, MenuItem, Select } from "@mui/material";
import React from "react";

function Dropdown(props) {
  const searchTerms = [
    { id:1, name: "책 제목", value: "title" },
    { id:2, name: "카테고리", value: "category" },
    { id:3, name: "가격 이하", value: "price" },
  ];

  const [terms, setTerms] = React.useState('');

  const handleChange = (event) => {
    setTerms(event.target.value);
    props.setCondition(event.target.value);
  };

  return (
    <FormControl className="dropdown" sx={{ m: 1, minWidth: 110 }}>
      <InputLabel id="select-label" style={{ color: "black" }}>
        검색 조건
      </InputLabel>
      <Select
        value={terms}
        label="search"
        onChange={handleChange}
        style={{ color: "black" }}
      >
        {searchTerms.map((item) => (
          <MenuItem name={item.name} key={item.id} value={item.name}>
            {item.name}
          </MenuItem>
        ))}
      </Select>
    </FormControl>
  );
}

export default Dropdown;
