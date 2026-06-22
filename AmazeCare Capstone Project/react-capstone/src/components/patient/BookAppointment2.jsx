import axios from "axios";
import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

const BookAppointment2 = () => {

    const navigate = useNavigate()
    const { state } = useLocation()
    const [symptom, setSymptom] = useState("");
    const [reason, setReason] = useState("");
    const [msg, setMsg] = useState("")
    const [errMsg, setErrMsg] = useState("")
    
    // Safety check in case user lands here without state
    if (!state) {
        navigate(-1);
        return null;
    }

    const postApi = `http://localhost:8080/api/appointment/book-step2/${state.doctor.id}`

    const Book = async (e) => {
        e.preventDefault()

        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        };
        const body = {
            date: state.selectedDate,
            symptoms: symptom,
            reason: reason,
            startTime: state.selectedSlot.startTime,
            endTime: state.selectedSlot.endTime
        }

        try {
            const response = await axios.post(postApi, body, config)
            console.log(response.data)
            setMsg("Booked Appointment Successfully")
            setSymptom("")
            setReason("")
            setTimeout(() => {
                navigate(-2)
            }, 3000)
        } catch (err) {
            console.log(err.response.data)
            setMsg("")
            setErrMsg(err.response.data.message)
            setTimeout(() => {
                navigate(-1)
            }, 4000);
        }
    }

    const formatDate = (date) => {
        return date.split("-").reverse().join("-")
    }

    const convertTime = (time) => {
        let hour = time.split(":")[0];
        let min = time.split(":")[1]
        const amOrPm = (hour >= 12) ? "PM" : "AM";
        hour = hour % 12
        if (hour == 0) {
            hour = 12
        }
        return `${hour}:${min} ${amOrPm}`;
    }

    return (
        <div
            className="dashboard-content flex-grow-1 p-4"
            style={{ minHeight: "calc(100vh - 56px)", minWidth: 0 }}
        >
            <div className="row justify-content-center">
                <div className="col-lg-10">
                    <div className="card border-0 shadow-sm">
                        <div className="card-body p-4">
                            
                            {/* Feedback Toasts */}
                            {msg && (
                                <div className="toast show align-items-center text-white bg-success border-0 w-100 mb-3" role="alert">
                                    <div className="d-flex">
                                        <div className="toast-body text-center w-100">{msg}</div>
                                        <button type="button" className="btn-close btn-close-white me-2 m-auto" onClick={() => setMsg("")}></button>
                                    </div>
                                </div>
                            )}
                            {errMsg && (
                                <div className="toast show align-items-center text-white bg-danger border-0 w-100 mb-3" role="alert">
                                    <div className="d-flex">
                                        <div className="toast-body text-center w-100">{errMsg}</div>
                                        <button type="button" className="btn-close btn-close-white me-2 m-auto" onClick={() => {setErrMsg(""); navigate(-1)}}></button>
                                    </div>
                                </div>
                            )}

                            <div className="mb-4">
                                <h4 className="fw-bold text-primary mb-1">Dr. {state.doctor.fullname}</h4>
                                <span className="text-muted fw-semibold">{state.doctor.specialty}</span>
                                <span className="mx-2 text-muted">•</span>
                                <span className="text-muted">{state.doctor.experience} Years Experience</span>
                            </div>

                            <div className="alert alert-success fw-semibold border-0 shadow-sm mb-4">
                                <i className="bi bi-check-circle me-2"></i>
                                Confirm your appointment details below
                            </div>

                            {/* Summary Cards */}
                            <div className="row g-3 mb-4">
                                <div className="col-md-4">
                                    <div className="border rounded p-3 d-flex align-items-center">
                                        <i className="bi bi-calendar-date fs-4 text-primary me-3"></i>
                                        <div>
                                            <small className="text-muted d-block">Date</small>
                                            <span className="fw-semibold">{formatDate(state.selectedDate)}</span>
                                        </div>
                                    </div>
                                </div>
                                <div className="col-md-4">
                                    <div className="border rounded p-3 d-flex align-items-center">
                                        <i className="bi bi-clock fs-4 text-primary me-3"></i>
                                        <div>
                                            <small className="text-muted d-block">Time Slot</small>
                                            <span className="fw-semibold">{convertTime(state.selectedSlot.startTime)} - {convertTime(state.selectedSlot.endTime)}</span>
                                        </div>
                                    </div>
                                </div>
                                <div className="col-md-4">
                                    <div className="border rounded p-3 d-flex align-items-center">
                                        <i className="bi bi-stopwatch fs-4 text-primary me-3"></i>
                                        <div>
                                            <small className="text-muted d-block">Duration</small>
                                            <span className="fw-semibold">{state.selectedSlot.duration} Minutes</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <form onSubmit={Book}>
                                <div className="mb-4">
                                    <label className="form-label fw-semibold">Symptoms *</label>
                                    <textarea
                                        className="form-control"
                                        rows="3"
                                        placeholder="Enter your symptoms" 
                                        value={symptom} 
                                        onChange={(e) => setSymptom(e.target.value)}
                                        required
                                    />
                                </div>

                                <div className="mb-4">
                                    <label className="form-label fw-semibold">Reason For Visit *</label>
                                    <textarea
                                        className="form-control"
                                        rows="3"
                                        placeholder="Enter reason for your visit" 
                                        value={reason} 
                                        onChange={(e) => setReason(e.target.value)}
                                        required
                                    />
                                </div>

                                <div className="d-flex justify-content-between border-top pt-4">
                                    <button
                                        type="button"
                                        className="btn btn-outline-secondary px-4" 
                                        onClick={() => navigate(-1)}
                                    >
                                        <i className="bi bi-arrow-left me-2"></i>
                                        Back
                                    </button>
                                    <button
                                        type="submit"
                                        className="btn btn-primary px-5"
                                        disabled={!symptom.trim() || !reason.trim()}
                                    >
                                        <i className="bi bi-calendar-check me-2"></i>
                                        Book Appointment
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default BookAppointment2;