import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";

export default function Login() {
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  console.log(name, password);

  async function login() {
    const body = {
      name,
      password,
    };
  
    try {
      const response = await fetch(`/api/user/login`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(body),
      });
  
      if (!response.ok) {
        throw new Error("Failed to login");
      }
  
      const data = await response.json();
  
      if (data.jwt) {
        localStorage.setItem("jwt", data.jwt);
        navigate("/home");
      }
    } catch (error) {
      console.error("Login error:", error);
    }
  }

  return (
    <div>
      <h1>Login</h1>
      <form className="form">
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
        <div>
          <button
            className="button"
            type="button"
            disabled={false}
            onClick={login}
          >
            Login
          </button>
        </div>
        <div>
          <Link to={"register"}>
            <button className="button" type="button" disabled={false}>
              Register
            </button>
          </Link>
        </div>
      </form>
    </div>
  );
}
