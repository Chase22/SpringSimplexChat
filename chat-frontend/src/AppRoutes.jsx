import { React } from 'react'
import { Router, Route } from 'react-router';


const AppRouter = () => { <Router history=true>
    <Switch>
        <Route exact path={"/"} component={Home} />
    </Switch>
</Router> }

export default AppRouter