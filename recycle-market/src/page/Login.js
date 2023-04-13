import {
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
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import React from "react";
import { useForm } from "react-hook-form";
import { Link } from "react-router-dom";
import { signin } from "../service/ApiService";

function Login() {
  // 폼을 만들기 위한 여러가지 요소 불러오기
  const { register, handleSubmit, getValues } = useForm();

  // 비밀번호 박스 암호화
  const [showPassword, setShowPassword] = React.useState(false);

  const handleClickShowPassword = () => setShowPassword((show) => !show);

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };

  const onValid = (data) => {
    console.log(data);

    const { phoneNumber, password } = getValues();
    signin({ phoneNumber: phoneNumber, password: password });
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
              id="phoneNumber"
              label="전화번호"
              name="phoneNumber"
              autoComplete="phoneNumber"
              {...register("phoneNumber")}
            />
          </Grid>
          <Grid item xs={12}>
          <FormControl {...register("password")} fullWidth sx={{ m: 1 }} variant="outlined">
              <InputLabel required >패스워드</InputLabel>
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
                label="password"
              />
            </FormControl>
          </Grid>
          <Grid item xs={12}>
            <Button type="submit" fullWidth variant="contained" color="primary">
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
    </Container>
  );
}

export default Login;
