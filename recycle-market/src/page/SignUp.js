import {
  Button,
  Container,
  FormControl,
  FormControlLabel,
  FormLabel,
  Grid,
  IconButton,
  InputAdornment,
  InputLabel,
  OutlinedInput,
  Radio,
  RadioGroup,
  TextField,
  Typography,
} from "@material-ui/core";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import React, { Component, useState } from "react";
import { Link } from "react-router-dom";
import { useForm } from "react-hook-form";
import { checkCertificateCode, sendEmail, signup } from "../service/ApiService";
import "./SignUp.css";

export default function SignUp() {
  const [isChecked, setIsChecked] = useState(false);
  const [value, setValue] = React.useState("female");

  const handleChange = (event) => {
    setValue(event.target.value);
    console.log(event.target.value);
  };

  // 폼을 만들기 위한 여러가지 요소 불러오기
  const { register, handleSubmit, getValues } = useForm();

  // 비밀번호 박스 암호화
  const [showPassword, setShowPassword] = React.useState(false);

  const handleClickShowPassword = () => setShowPassword((show) => !show);

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };

  // 데이터 전송시 작동할 함수 정의
  const onValid = (data) => {
    console.log(data);

    const { username, phoneNumber, password, address1, address2 } = getValues();
    signup({
      phoneNumber: phoneNumber,
      username: username,
      password: password,
      address1: address1,
      address2: address2,
    }).then((respone) => {
      window.location.href = "/login";
    });
  };

  return (
    <Container component="main" maxWidth="xs" style={{ marginTop: "8%" }}>
      <form noValidate onSubmit={handleSubmit(onValid)}>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <Typography component="h1" variant="h5">
              계정 생성
            </Typography>
          </Grid>
          <Grid item xs={12}>
            <TextField
              autoComplete="username"
              name="username"
              variant="outlined"
              required
              fullWidth
              id="username"
              label="사용자 이름"
              autoFocus
              {...register("username")}
            />
          </Grid>
          <Grid container spacing={12}>
            <Grid item xs={10}>
              <TextField
                autoComplete="phoneNumber"
                name="phoneNumber"
                variant="outlined"
                required
                fullWidth
                id="phoneNumber"
                label="전화번호"
                autoFocus
                {...register("phoneNumber")}
              />
            </Grid>
            <Grid item xs={2}>
              <div
                className="user-btn"
                type="button"
                onClick={() => {
                  const { phoneNumber } = getValues();
                  console.log(phoneNumber, "인증번호 전송");
                }}
              >
                코드 전송
              </div>
            </Grid>
          </Grid>
          <Grid container spacing={12}>
            <Grid item xs={10}>
              <TextField
                required
                fullWidth
                id="phoneNumber"
                label="인증 코드"
                autoFocus
                {...register("code")}
              />
            </Grid>
            <Grid item xs={2}>
              <div
                className="user-btn"
                type="button"
                onClick={() => {
                  const { code } = getValues();
                  console.log("인증번호 확인");
                  setIsChecked(true);
                }}
                fullWidth
              >
                확인
              </div>
            </Grid>
          </Grid>
          <Grid item xs={12}>
            <FormControl
              {...register("password")}
              fullWidth
              sx={{ m: 1 }}
              variant="outlined"
            >
              <InputLabel required>패스워드</InputLabel>
              <OutlinedInput
                type={showPassword ? "text" : "password"}
                endAdornment={
                  <InputAdornment position="end">
                    <IconButton
                      aria-label="toggle password visibility"
                      onClick={handleClickShowPassword}
                      onMouseDown={handleMouseDownPassword}
                      edge="end"
                    >
                      {showPassword ? <VisibilityOff /> : <Visibility />}
                    </IconButton>
                  </InputAdornment>
                }
                label="Password"
              />
            </FormControl>
          </Grid>
          <Grid container spacing={12}>
            <Grid item xs={10}>
              <TextField
                required
                fullWidth
                id="address"
                label="주소"
                autoFocus
                disabled
                {...register("address1")}
              />
            </Grid>
            <Grid item xs={2}>
              <div
                className="user-btn"
                type="button"
                onClick={()=>console.log("검색 API로 검색")}
                fullWidth
              >
                검색
              </div>
            </Grid>
          </Grid>
          <Grid item xs={12}>
            <TextField
              autoComplete="address2"
              name="address2"
              variant="outlined"
              required
              fullWidth
              id="address2"
              label="상세 주소"
              autoFocus
              {...register("address2")}
            />
          </Grid>
          <Grid item xs={12}>
            <FormControl>
              <FormLabel id="demo-row-radio-buttons-group-label">
                Gender
              </FormLabel>
              <RadioGroup
                row
                name="row-radio-buttons-group"
                value={value}
                onChange={handleChange}
              >
                <FormControlLabel
                  value="0"
                  control={<Radio />}
                  label="판매자"
                />
                <FormControlLabel
                  value="1"
                  control={<Radio />}
                  label="구매자"
                />
              </RadioGroup>
            </FormControl>
          </Grid>
          <Grid item xs={12}>
            <Button
              disabled={isChecked ? false : true}
              type="submit"
              fullWidth
              variant="contained"
              color="primary"
            >
              계정생성
            </Button>
          </Grid>
        </Grid>
        <Grid container justifyContent="flex-end">
          <Grid item xs={12}>
            <Button
              style={{ marginTop: "5px" }}
              fullWidth
              variant="contained"
              color="secondary"
            >
              로그인으로 돌아가기
            </Button>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}
