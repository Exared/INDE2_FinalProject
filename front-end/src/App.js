import './App.css';
import ErrorPage from './ErrorPage/ErrorPage';
import Home from './Home/HomePage';
import Alert from './Reports/ReportsPage';
import Statistics from './Statistics/StatisticsPage';

import {
  BrowserRouter as Router,
  Route,
  Switch,
} from "react-router-dom";

function App() {
  return (
  <Router>
  <Switch>
    <Route exact path="/" component={Home} />
    <Route exact path="/home" component={Home} />
    <Route path="/statistics" component={Statistics} />
    <Route path="/alerts" component={Alert} />
    <Route path ="*" component = {ErrorPage}/>
  </Switch>
  </Router>
  );
}

export default App;
