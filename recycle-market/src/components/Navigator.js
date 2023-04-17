import { AppBar, Button, Grid, Toolbar, Typography } from '@material-ui/core'
import React from 'react'
import { Link } from 'react-router-dom'
import { signout } from '../service/ApiService'

function Navigator() {
  const accessToken = localStorage.getItem("ACCESS_TOKEN");

  return (
      // navigationBar
    <AppBar position="static">
      <Toolbar>
        <Grid justifyContent="space-between" container>
          <Grid item><div style={{cursor:"pointer"}} onClick={()=>window.location.href="/"}>
          <Typography variant="h6">Recycle-market</Typography>
          </div>
          </Grid>
          <Grid item>
          <Button color="inherit" href="/profile">
              my
            </Button>
            {accessToken != null? (<Button color="inherit" onClick={signout}>
              logout
            </Button>):(<Button color="inherit" href="/login">
              login
            </Button>)}
            
          </Grid>
        </Grid>
      </Toolbar>
    </AppBar>
  )
}

export default Navigator