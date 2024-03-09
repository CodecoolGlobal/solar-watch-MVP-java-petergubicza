import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Update() {
  const [cityName, setCityName] = useState("");
  const [date, setDate] = useState("");
  const [sunrise, setSunrise] = useState("");
  const [sunset, setSunset] = useState("");
  const navigate = useNavigate();

  async function handleUpdate() {
    const body = {
      cityName,
      date,
      sunrise,
      sunset,
    };

    await fetch(`/api/solar-watch/update`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("jwt")}`,
      },
      body: JSON.stringify(body),
    });
  }

  return (
    <div>
      <h2>Update Solar Data</h2>
      <form>
        <div>
          <label>
            City Name:
            <input
              type="text"
              value={cityName}
              onChange={(e) => setCityName(e.target.value)}
            />
          </label>
        </div>
        <div>
          <label>
            Date:
            <input
              type="text"
              value={date}
              onChange={(e) => setDate(e.target.value)}
            />
          </label>
        </div>
        <div>
          <label>
            Sunrise:
            <input
              type="text"
              value={sunrise}
              onChange={(e) => setSunrise(e.target.value)}
            />
          </label>
        </div>
        <div>
          <label>
            Sunset:
            <input
              type="text"
              value={sunset}
              onChange={(e) => setSunset(e.target.value)}
            />
          </label>
        </div>
        <button type="button" onClick={handleUpdate}>
          Update
        </button>
      </form>
      <button type="button" onClick={() => navigate("/home")}>
          Back
        </button>
    </div>
  );
}
