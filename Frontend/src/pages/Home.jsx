import { useEffect, useState } from "react";
import API from "../services/api";
import { Link } from "react-router-dom";

function Home() {
  const [rooms, setRooms] = useState([]);
  const [aiQuery, setAiQuery] = useState("");
  const [aiRooms, setAiRooms] = useState([]);
  const [loading, setLoading] = useState(false);
  const [aiSearched, setAiSearched] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchRooms = async () => {
      try {
        const { data } = await API.get("/rooms");
        setRooms(data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchRooms();
  }, []);

  const handleAISearch = async () => {
    if (!aiQuery.trim()) return;

    try {
      setLoading(true);
      setError("");

      const { data } = await API.post("/ai/search", {
        message: aiQuery
      });

      setAiRooms(data.rooms);
      setAiSearched(true);
    } catch (error) {
      console.error(error);
      setError("AI search failed. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">

      {/* AI SEARCH */}
      <div className="ai-search-container">

        <h1>RoomEase AI</h1>

        <p>
          Tell us what kind of room you're looking for.
        </p>

        <div className="ai-search-box">

          <input
            type="text"
            placeholder='Try: "Luxury room under ₹10,000 for 2 guests"'
            value={aiQuery}
            onChange={(e) => setAiQuery(e.target.value)}
            onKeyDown={(e) => {
              if (e.key === "Enter") {
                handleAISearch();
              }
            }}
          />

          <button onClick={handleAISearch} disabled={loading}>
            {loading ? "Searching..." : "Search with AI"}
          </button>

        </div>

        {error && (
          <p style={{ color: "red", marginTop: "10px" }}>
            {error}
          </p>
        )}

      </div>


      {/* AI RESULTS */}

      {aiSearched && (
        <div>

          <h2 style={{ marginBottom: "30px" }}>
            AI Recommended Rooms
          </h2>

          {aiRooms.length === 0 ? (
            <p>No rooms matched your request.</p>
          ) : (

            <div className="room-grid">

              {aiRooms.map((room) => (

                <div key={room._id} className="room-card">

                  <img
                    src={room.image}
                    alt={room.type}
                  />

                  <div className="room-card-content">

                    <h3>{room.type}</h3>

                    <div className="card-footer">

                      <p className="price">
                        ₹ {room.price} / night
                      </p>

                      <Link
                        to={`/room/${room._id}`}
                        className="button"
                      >
                        View Details
                      </Link>

                    </div>

                  </div>

                </div>

              ))}

            </div>

          )}

        </div>
      )}


      {/* NORMAL ROOMS */}

      {!aiSearched && (
        <>
          <h1 style={{ marginBottom: "30px" }}>
            Available Rooms
          </h1>

          <div className="room-grid">

            {rooms.map((room) => (

              <div key={room._id} className="room-card">

                <img
                  src={room.image}
                  alt={room.type}
                />

                <div className="room-card-content">

                  <h3>{room.type}</h3>

                  <div className="card-footer">

                    <p className="price">
                      ₹ {room.price} / night
                    </p>

                    <Link
                      to={`/room/${room._id}`}
                      className="button"
                    >
                      View Details
                    </Link>

                  </div>

                </div>

              </div>

            ))}

          </div>
        </>
      )}

    </div>
  );
}

export default Home;