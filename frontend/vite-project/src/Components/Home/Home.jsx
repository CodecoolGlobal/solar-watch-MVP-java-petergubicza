import { useState } from "react";

export default function Home() {
  const [cityName, setCityName] = useState("");
  const [date, setDate] = useState("");
  const [sunrise, setSunrise] = useState("");
  const [sunset, setSunset] = useState("");
  const isAdmin = true;
  const [apiResponse, setApiResponse] = useState(null);

  async function handleSubmit() {

      const response = await fetch(`/api/solar-watch/solar-times?cityName=${cityName}&date=${date}`, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwt")}`,
        },
      });
      const data = await response.json();

    setApiResponse(data);
  }

  async function handleUpdate() {
    const body = {
      cityName,
      date,
      sunrise,
      sunset,
    };

    async function patchData(url, body) {
      const response = await fetch(url, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("jwt")}`,
        },
        body: JSON.stringify(body),
      });
      const data = await response.json();
      return data;
    }

    await patchData(`/api/solar-watch/update`, body);
    setCityName("");
    setDate("");
    setSunrise("");
    setSunset("");
  }

  async function handleDelete() {
    const body = {
      cityName,
      date,
      sunrise,
      sunset,
    };

    async function deleteData(url, body) {
      const response = await fetch(url, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("jwt")}`,
        },
        body: JSON.stringify(body),
      });
      const data = await response.json();
      return data;
    }

    await deleteData(`/api/solar-watch/delete`, body);
    setCityName("");
    setDate("");
    setSunrise("");
    setSunset("");
  }

  return (
    <div>
      <h2>Solar Watch</h2>
      {apiResponse && (
        <div>
          sunrise: {apiResponse.sunrise}
          sunset: {apiResponse.sunset}
        </div>
      )}
      <form >
        <label>
          City Name:
          <input
            type="text"
            value={cityName}
            onChange={(e) => setCityName(e.target.value)}
          />
        </label>
        <label>
          Date:
          <input
            type="text"
            value={date}
            onChange={(e) => setDate(e.target.value)}
          />
        </label>
        <button onClick={(e) => { e.preventDefault(); handleSubmit(); }}>Submit</button>
      </form>
      {isAdmin && (
        <div>
          <h3>Admin Actions</h3>
          <button onClick={handleUpdate}>Update</button>
          <button onClick={handleDelete}>Delete</button>
        </div>
      )}
    </div>
  );
}
