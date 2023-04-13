import { render } from "react-dom";
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
  IconButton,
  Select,
  InputLabel,
  FormControl,
  MenuItem,
  TextField,
} from "@material-ui/core";
import call, { signout } from "./service/ApiService";
import DeleteOutlined from "@material-ui/icons/DeleteOutlined";
import Paging from "./components/Paging";
import { Pagination } from "@mui/material";
import "./App.css";

function App() {
  const products = [
    {
      id: 1,
      name: "상품1",
      category: "clothes",
      date: "2022-05-01",
      image: "https://via.placeholder.com/150",
    },
    {
      id: 2,
      name: "상품2",
      category: "food",
      date: "2022-05-02",
      image: "https://via.placeholder.com/150",
    },
    {
      id: 3,
      name: "상품3",
      category: "electronics",
      date: "2022-05-03",
      image: "https://via.placeholder.com/150",
    },
    {
      id: 4,
      name: "상품4",
      category: "clothes",
      date: "2022-05-04",
      image: "https://via.placeholder.com/150",
    },
    {
      id: 5,
      name: "상품5",
      category: "food",
      date: "2022-05-05",
      image: "https://via.placeholder.com/150",
    },
    {
      id: 6,
      name: "상품6",
      category: "clothes",
      date: "2022-05-06",
      image: "https://via.placeholder.com/150",
    },
    {
      id: 7,
      name: "상품7",
      category: "food",
      date: "2022-05-07",
      image: "https://via.placeholder.com/150",
    },
    {
      id: 8,
      name: "상품8",
      category: "electronics",
      date: "2022-05-08",
      image: "https://via.placeholder.com/150",
    },
    {
      id: 9,
      name: "상품9",
      category: "clothes",
      date: "2022-05-09",
      image: "https://via.placeholder.com/150",
    },
    {
      id: 10,
      name: "상품10",
      category: "food",
      date: "2022-05-10",
      image: "https://via.placeholder.com/150",
    },
    {
      id: 11,
      name: "상품11",
      category: "electronics",
      date: "2022-05-11",
      image: "https://via.placeholder.com/150",
    },
    {
      id: 12,
      name: "상품12",
      category: "clothes",
      date: "2022-05-12",
      image: "https://via.placeholder.com/150",
    },
  ];

  const [category, setCategory] = useState("");
  const [search, setSearch] = useState("");
  const [page, setPage] = useState(1);
  const itemsPerPage = 6;
  const pages = Math.ceil(products.length / itemsPerPage);

  const handleChange = (event, value) => {
    setPage(value);
  };
  const handleCategoryChange = (event) => {
    setCategory(event.target.value);
  };

  const handleSearchChange = (event) => {
    setSearch(event.target.value);
  };

  const handleSearch = () => {
    console.log(`Search category: ${category}, keyword: ${search}`);
  };

  const handleSell = () => {
    console.log("Go to sell page");
  };

  return (
    <div>
      <div className="search-bar">
        <FormControl variant="outlined">
          <InputLabel id="category-select-label">카테고리 선택</InputLabel>
          <Select
            labelId="category-select-label"
            id="category-select"
            value={category}
            onChange={handleCategoryChange}
            label="카테고리 선택"
          >
            <MenuItem value="">전체</MenuItem>
            <MenuItem value="clothes">의류</MenuItem>
            <MenuItem value="food">음식</MenuItem>
            <MenuItem value="electronics">전자제품</MenuItem>
          </Select>
        </FormControl>

        <TextField
          label="상품 검색"
          variant="outlined"
          value={search}
          onChange={handleSearchChange}
        />

        <Button variant="contained" color="primary" onClick={handleSearch}>
          검색
        </Button>

        <Button variant="contained" color="secondary" onClick={handleSell}>
          판매하기
        </Button>
      </div>
      <Grid container spacing={3} className="centered-grid-container">
        {products
          .slice((page - 1) * itemsPerPage, page * itemsPerPage)
          .map((product) => (
            <Grid item xs={12} sm={6} md={4} key={product.id}>
              <img src={product.image} alt={product.name} />
              <p>{product.name}</p>
              <p>{product.category}</p>
              <p>{product.date}</p>
            </Grid>
          ))}
      </Grid>
      <Pagination className="select-page" count={pages} page={page} onChange={handleChange} />
    </div>
  );
}

export default App;
