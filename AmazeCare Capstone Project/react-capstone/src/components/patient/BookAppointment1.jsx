import axios from "axios";
import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

const BookAppointment1 = () => {

    const navigate = useNavigate()
    const [selectedDate, setSelectedDate] = useState("");
    const [selectedSlot, setSelectedSlot] = useState();
    const { state } = useLocation()

    const [slots, setSlots] = useState([])
    const getApi = `http://localhost:8080/api/appointment/book-step1/${state.id}`

    useEffect(() => {

        if (!selectedDate) return;
        const getSlots = async () => {
            try {
                const response = await axios.get(getApi,
                    {
                        headers: {
                            Authorization: "Bearer " + localStorage.getItem("token")
                        },
                        params: {
                            appointmentDate: selectedDate
                        }
                    }
                );
                console.log(response.data.appointmentSlots)
                setSlots(response.data.appointmentSlots)

            } catch (err) {
                console.log(err?.response?.data);
            }
        };

        getSlots();

    }, [selectedDate]);

    function convertTime(time) {
        let hour = time.split(":")[0];
        let min = time.split(":")[1]
        const amOrPm = (hour >= 12) ? "PM" : "AM";
        hour = hour % 12
        if (hour === 0) {
            hour = 12
        }
        return `${hour}:${min} ${amOrPm}`;
    }

    return (

        <div
            className="dashboard-content flex-grow-1 p-4"
            style={{ minHeight: "calc(100vh - 56px)", minWidth: 0 }}
        >
            <button
                type="button"
                className="btn btn-outline-secondary px-4 mb-4"
                onClick={() => {
                    navigate(-1)
                }}
            >
                <i className="bi bi-arrow-left me-2"></i>
                Back
            </button>

            <h2 className="fw-bold mb-4">
                Book Appointment
            </h2>

            <div className="card border-0 shadow-sm">

                <div className="card-body">

                    <div className="d-flex align-items-center mb-4">
                        <div>
                            <h5 className="fw-bold mb-1">
                                Dr. {state.fullname}
                            </h5>

                            <span className="text-muted fw-semibold">
                                {state.specialty}
                            </span>

                            <span className="mx-2 text-muted">•</span>

                            <span className="text-muted">
                                {state.experience} Years Experience
                            </span>
                        </div>
                    </div>

                    <div className="alert alert-primary text-center fw-semibold border-0 shadow-sm mb-4">
                        <i className="bi bi-calendar-check me-2"></i>
                        Check doctor availability and book your appointment
                    </div>

                    <div className="mb-4">
                        <label className="form-label fw-semibold">
                            Select Appointment Date
                        </label>

                        <input
                            type="date"
                            className="form-control"
                            min={new Date().toISOString().split("T")[0]}
                            value={selectedDate}
                            onChange={(e) => {
                                setSelectedDate(e.target.value);
                                setSelectedSlot([]); // Reset slot when date changes
                            }}
                            style={{ maxWidth: "250px" }}
                        />
                    </div>

                    <div>
                        <h5 className="fw-bold mb-3 mt-4">
                            Available Time Slots
                        </h5>

                        <div className="row g-3">
                            {slots.length > 0 ? (
                                slots.map((slot, index) => (
                                    <div
                                        className="col-lg-3 col-md-4 col-sm-6"
                                        key={index}
                                    >
                                        <button
                                            className={`btn w-100 ${selectedSlot?.startTime === slot.startTime
                                                ? "btn-primary shadow-sm"
                                                : "btn-outline-primary"
                                                }`}
                                            onClick={() => setSelectedSlot(slot)}
                                        >
                                            <i className="bi bi-clock me-2"></i>
                                            {convertTime(slot.startTime)} - {convertTime(slot.endTime)}
                                        </button>
                                    </div>
                                ))
                            ) : (
                                <div className="col-12 text-center py-4 text-muted bg-light rounded-3">
                                    {selectedDate
                                        ? "No time slots available for this date. Please choose another date."
                                        : "Please select a date to view available time slots."}
                                </div>
                            )}
                        </div>
                    </div>

                    <div className="d-flex justify-content-end mt-5 border-top pt-4">
                        <button
                            className="btn btn-success px-5"
                            disabled={!selectedDate || !selectedSlot?.startTime}
                            onClick={() => {
                                navigate("/patient/book-appointment2",
                                    {
                                        state: {
                                            doctor: state,
                                            selectedDate,
                                            selectedSlot
                                        }
                                    }
                                )
                            }}
                        >
                            Next Step
                            <i className="bi bi-arrow-right ms-2"></i>
                        </button>
                    </div>

                </div>

            </div>

        </div >

    );
};

export default BookAppointment1;