import {
  CircularProgress,
  Container,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  Select,
  TextField,
} from "@material-ui/core";
import React, { useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import call, { postFormData } from "../service/ApiService";
import "./Sell.css";

function Sell() {
  const [isLoading, setIsLoading] = useState(false);
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
    setIsLoading(true);
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
      console.log(key, ":", formData.get(key));
    }

    postFormData("/api/v1/products", formData)
      .then((response) => {
        console.log(response);
        alert("등록에 성공했습니다!");
        window.location.replace("/");
      })
      .catch((error) => {
        setIsLoading(false);
      });
  };

  return (
    <div className="container">
      <div className="form-container">
        <form className="form" onSubmit={onValid}>
          <div
            className="form-header"
            style={{ display: "flex", alignItems: "center" }}
          >
            <FormControl variant="outlined" style={{ flexBasis: "28%" }}>
              <InputLabel id="category-select-label">카테고리 선택</InputLabel>
              <Select
                labelId="category-select-label"
                id="category-select"
                value={category}
                onChange={handleCategoryChange}
                label="카테고리 선택"
              >
                <MenuItem value={null} disabled>
                  전체
                </MenuItem>
                <MenuItem value="의류">의류</MenuItem>
                <MenuItem value="음식">음식</MenuItem>
                <MenuItem value="운동용품">운동용품</MenuItem>
                <MenuItem value="전자제품">전자제품</MenuItem>
                <MenuItem value="기타">기타</MenuItem>
              </Select>
            </FormControl>
            <TextField
              variant="outlined"
              required
              label="상품명"
              value={productName}
              onChange={handleProductNameChange}
              className="search-input"
              style={{ flexBasis: "70%" }}
            />
          </div>
          <div className="form-body">
            <label style={{ marginBottom: "20px" }} className="price">
              시작 가격 :
              <input
                type="number"
                min="0"
                value={startPrice}
                onChange={handleStartPriceChange}
              />{" "}
              원
            </label>
            <div className="date-picker" style={{ marginBottom: "20px" }}>
              <label
                style={{
                  display: "inline-block",
                  width: "100px",
                  marginRight: "10px",
                }}
              >
                종료시간 :
              </label>
              <DatePicker
                selected={selectedDate}
                onChange={handleDateChange}
                showTimeSelect
                timeIntervals={1}
                minDate={new Date()}
                dateFormat="MM/dd/yyyy h:mm aa"
                style={{ display: "inline-block" }}
              />
            </div>
          </div>
          <TextField
            variant="outlined"
            required
            multiline
            rows={4}
            label="상품 설명"
            value={productDescription}
            onChange={handleProductDescriptionChange}
            className="product-description"
            style={{ marginBottom: "20px", width: "100%" }}
          />
          <div style={{ marginBottom: "20px" }} className="file-upload">
            <label className="image-upload">
              이미지 파일 첨부 :
              <input type="file" accept="image/*" onChange={handleFileChange} />
            </label>
          </div>
          {fileUrl && (
            <div className="form__file-name">
              <img
                src={fileUrl}
                alt="첨부된 파일"
                style={{ maxWidth: "100%" }}
              />
            </div>
          )}
          <div className="register">
            <button
              disabled={isLoading}
              onClick={() => onValid()}
              type="button"
            >
              {isLoading ? (
                <CircularProgress size={24} color="primary" />
              ) : (
                "등록하기"
              )}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default Sell;
