import {
  Container,
  Grid,
  MenuItem,
  Select,
  TextField,
} from "@material-ui/core";
import React, { useState } from "react";
import { DateTimePicker } from "@material-ui/pickers";

function Sell() {
  const [category, setCategory] = useState("");
  const [productName, setProductName] = useState("");
  const [productDescription, setProductDescription] = useState("");
  const [startPrice, setStartPrice] = useState(0);
  const [selectedDate, handleDateChange] = useState(new Date());
  const [file, setFile] = useState(null);
  const [fileUrl, setFileUrl] = useState(null); // 파일 URL을 상태로 관리

  const handleCategoryChange = (event) => {
    setCategory(event.target.value);
  };

  const handleProductNameChange = (event) => {
    setProductName(event.target.value);
  };

  const handleProductDescriptionChange = (event) => {
    setProductDescription(event.target.value);
  };

  const handleStartPriceChange = (event) => {
    setStartPrice(Number(event.target.value));
  };

  const handleFileChange = (event) => {
    const selectedFile = event.target.files[0];
    setFile(selectedFile);
    setFileUrl(URL.createObjectURL(selectedFile)); // 파일 URL을 생성하여 상태로 저장
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // Implement submission logic here
  };

  return (
    <Container component="main" maxWidth="xs" style={{ marginTop: "8%" }}>
      {" "}
      <form onSubmit={handleSubmit}>
        <Grid container spacing={2}>
          <Grid item xs={12} sm={6}>
            <Select value={category} onChange={handleCategoryChange}>
              <MenuItem value="카테고리">카테고리</MenuItem>
              <MenuItem value="의류">의류</MenuItem>
              <MenuItem value="가전제품">가전제품</MenuItem>
              <MenuItem value="가구">가구</MenuItem>
            </Select>
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField
              variant="outlined"
              required
              label="상품명"
              value={productName}
              onChange={handleProductNameChange}
            />
          </Grid>
        </Grid>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <TextField
              variant="outlined"
              required
              label="상품 설명"
              value={productDescription}
              onChange={handleProductDescriptionChange}
            />
          </Grid>
        </Grid>
        <Grid container spacing={2}>
          <Grid item xs={12} sm={6}>
            <label>
              시작 가격:
              <input
                type="number"
                min="0"
                value={startPrice}
                onChange={handleStartPriceChange}
              />
            </label>
          </Grid>
          <Grid item xs={12} sm={6}>
            <div>
              <label>
                종료 시간:

              </label>
            </div>
          </Grid>
        </Grid>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <label>
              이미지 파일 첨부:
              <input type="file" accept="image/*" onChange={handleFileChange} />
            </label>
          </Grid>
        </Grid>
        {fileUrl && (
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <img
                src={fileUrl}
                alt="첨부된 파일"
                style={{ maxWidth: "100%" }}
              />
            </Grid>
          </Grid>
        )}
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <button type="submit">등록하기</button>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}

export default Sell;
