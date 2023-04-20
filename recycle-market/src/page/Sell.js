import {
  Container,
  Grid,
  MenuItem,
  Select,
  TextField,
} from "@material-ui/core";
import React, { useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import call, { postFormData } from "../service/ApiService";

function Sell() {
  const [category, setCategory] = useState("");
  const [productName, setProductName] = useState("");
  const [productDescription, setProductDescription] = useState("");
  const [startPrice, setStartPrice] = useState(0);
  const [file, setFile] = useState();
  const [fileUrl, setFileUrl] = useState(); // 파일 URL을 상태로 관리
  const [selectedDate, setSelectedDate] = useState(new Date());

  const handleDateChange = (date) => {
    const dateObj = new Date(date);
    const convertedDate = new Date(
      dateObj.getTime() - dateObj.getTimezoneOffset() * 60000
    ).toISOString();
    console.log(convertedDate);
    setSelectedDate(date);
  };

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

  const onValid = (event) => {
    const convertedDate = new Date(
      selectedDate.getTime() - selectedDate.getTimezoneOffset() * 60000
    )
      .toISOString()
      .slice(0, -5);

    const formData = new FormData();
    formData.append("name", productName);
    formData.append("description", productDescription);
    formData.append("category", category);
    formData.append("endDate", convertedDate);
    formData.append("startPrice", startPrice);
    formData.append("image", file);

    for (let key of formData.keys()) {
      console.log(key, ":", formData.get(key));}

    postFormData("/api/v1/products", formData).then((response) => {
      console.log(response);
    });
  };

  return (
    <Container component="main" maxWidth="xs" style={{ marginTop: "8%" }}>
      {" "}
      <form onSubmit={onValid}>
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
                <DatePicker
                  selected={selectedDate}
                  onChange={handleDateChange}
                  showTimeSelect
                  timeIntervals={15}
                  minDate={new Date()}
                  dateFormat="MM/dd/yyyy h:mm aa"
                />
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
            <button onClick={() => onValid()} type="button">
              등록하기
            </button>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}

export default Sell;
