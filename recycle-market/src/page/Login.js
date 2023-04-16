import {
  Avatar,
  Button,
  Container,
  FormControl,
  Grid,
  IconButton,
  InputAdornment,
  InputLabel,
  OutlinedInput,
  TextField,
  Typography,
} from "@material-ui/core";
import { LockOutlined, Visibility, VisibilityOff } from "@material-ui/icons";
import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { Link } from "react-router-dom";
import { signin } from "../service/ApiService";
import { makeStyles } from "@material-ui/core/styles";

const useStyles = makeStyles((theme) => ({
  paper: {
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    paddingTop: theme.spacing(8),
    paddingBottom: theme.spacing(4),
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: "100%", // Fix IE 11 issue.
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
  loader: {
    position: "absolute",
    top: "50%",
    left: "50%",
    marginTop: -12,
    marginLeft: -12,
  },
  logo: {
    height: 60,
    marginBottom: theme.spacing(2),
  },
}));

function Login() {
  const classes = useStyles();
  const [isLoading, setIsLoading] = useState(false);

  // 폼을 만들기 위한 여러가지 요소 불러오기
  const { register, handleSubmit, getValues } = useForm();

  // 비밀번호 박스 암호화
  const [showPassword, setShowPassword] = React.useState(false);

  const handleClickShowPassword = () => setShowPassword((show) => !show);

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };

  const onValid = (data) => {
    setIsLoading(true);
    setTimeout(() => {
      const { phoneNumber, password } = getValues();
      console.log(phoneNumber, password);
      signin({ phoneNumber: phoneNumber, password: password });
      setIsLoading(false);
    }, 2000);
  };

  return (
    <Container component="main" maxWidth="xs">
      <div className={classes.paper}>
      <img src="/logo.png" alt="로고" className="logo" />
        <Typography component="h1" variant="h5">
          로그인
        </Typography>
        <form noValidate onSubmit={handleSubmit(onValid)}>
          {" "}
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField
                variant="outlined"
                required
                fullWidth
                id="phoneNumber"
                label="전화번호"
                name="phoneNumber"
                autoComplete="phoneNumber"
                {...register("phoneNumber")}
              />
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
                  name="password"
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
                  label="password"
                />
              </FormControl>
            </Grid>
            <Grid item xs={12}>
              <Button
                type="submit"
                fullWidth
                variant="contained"
                color="primary"
              >
                로그인
              </Button>
            </Grid>
            <Grid item xs={12}>
              <Link to="/signup" variant="body2">
                <Button fullWidth variant="contained">
                  회원가입
                </Button>
              </Link>
            </Grid>
          </Grid>
        </form>
      </div>
    </Container>
  );
}

export default Login;
