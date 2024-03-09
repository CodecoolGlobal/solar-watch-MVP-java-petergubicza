import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";

export default function Register() {
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const [isAdmin, setIsAdmin] = useState(false);
  const navigate = useNavigate();

  async function registerUser() {
    const body = {
      name,
      password,
      isAdmin,
    };

    const response = await fetch("/api/user/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(body),
    });

    const data = await response.json();

    if (data) {
      navigate("/");
    }
  }

  function handleRegister(e) {
    e.preventDefault();
    registerUser();
  }

  return (
    <div>
      <h1>Register</h1>
      <form className="form" onSubmit={handleRegister}>
        <div>
          <label htmlFor="userName">Username: </label>
          <input
            className="inputField"
            name="userName"
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
        </div>
        <div>
          <label htmlFor="password">Password: </label>
          <input
            className="inputField"
            name="password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        {isAdmin && (
          <div>
            <label>
              <input
                type="checkbox"
                checked={isAdmin}
                onChange={() => setIsAdmin(!isAdmin)}
              />
              Admin
            </label>
          </div>
        )}
        <div>
          <button className="button" type="submit">
            Register
          </button>
          <Link to={"/"}>
            <button className="button">Back</button>
          </Link>
        </div>
      </form>
    </div>
  );
}
