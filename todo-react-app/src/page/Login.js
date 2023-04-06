import {
  Button,
  Container,
  Grid,
  TextField,
  Typography,
} from "@material-ui/core";
import React from "react";
import { useForm } from "react-hook-form";
import { Link } from "react-router-dom";
import { signin } from "../service/ApiService";

function Login() {
  // 폼을 만들기 위한 여러가지 요소 불러오기
  const { register, handleSubmit, getValues } = useForm();

  const onValid = (data) => {
    console.log(data);

    const { email, password } = getValues();
    signin({ email: email, password: password });
  };

  return (
    <Container component="main" maxWidth="xs" style={{ marginTop: "8%" }}>
      <Grid container spacing={2}>
        <Typography component="h1" variant="h5">
          로그인
        </Typography>
      </Grid>
      <form noValidate onSubmit={handleSubmit(onValid)}>
        {" "}
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <TextField
              variant="outlined"
              required
              fullWidth
              id="email"
              label="이메일 주소"
              name="email"
              autoComplete="email"
              {...register("email")}
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              variant="outlined"
              required
              fullWidth
              id="password"
              label="패스워드"
              name="password"
              autoComplete="password"
              {...register("password")}
            />
          </Grid>
          <Grid item xs={12}>
            <Button type="submit" fullWidth variant="contained" color="primary">
              로그인
            </Button>
          </Grid>
          <Link to="/signup" variant="body2">
            <Grid item>계정이 없습니까? 여기서 가입하세요.</Grid>
          </Link>
        </Grid>
      </form>
    </Container>
  );
}

export default Login;
