import { AppBar, Button, Grid, Toolbar, Typography } from "@material-ui/core";
import React from "react";
import { Link } from "react-router-dom";
import { signout } from "../service/ApiService";

function Navigator() {
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  const role = localStorage.getItem("ROLE");

  return (
    // navigationBar
    <AppBar position="static">
      <Toolbar>
        <Grid container justifyContent="space-between" alignItems="center">
          <Grid item>
            <div
              style={{ cursor: "pointer" }}
              onClick={() => (window.location.href = "/")}
            >
              <Typography variant="h6">Recycle-market</Typography>
            </div>
          </Grid>
          <Grid item>
            {accessToken ? (
              <>
                <Button color="inherit" component={Link} to="/profile">
                  프로필
                </Button>
                <Button color="inherit" component={Link} to="/transaction">
                  내 거래
                </Button>
                {role == "BUYER" ? (
                  <Button color="inherit" component={Link} to="/bid">
                    내 입찰
                  </Button>
                ) : (
                  <></>
                )}
                <Button color="inherit" onClick={signout}>
                  logout
                </Button>
              </>
            ) : (
              <>
                <Button color="inherit" component={Link} to="/login">
                  login
                </Button>
              </>
            )}
          </Grid>
        </Grid>
      </Toolbar>
    </AppBar>
  );
}

export default Navigator;
