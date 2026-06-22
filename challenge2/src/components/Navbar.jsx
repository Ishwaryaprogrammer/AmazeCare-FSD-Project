import { NavLink } from "react-router-dom";

function Navbar() {
  return (
    <nav className="navbar border border-dark">
      <div className="d-flex container">
        <div>
          <NavLink className="btn btn-primary me-2" to="/">User List</NavLink>
          <NavLink className="btn btn-primary me-2" to="/add-user">Add User</NavLink>
          <NavLink className="btn btn-primary" to="/task">List</NavLink>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;