import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

const ManageAppointment = () => {

    const { docid } = useParams()

    const [direction, setDirection] = useState("DESC")
    const [date, setDate] = useState("")
    const [word, setWord] = useState("")
    const [currentPage, setCurrentPage] = useState(0);
    const [size, setSize] = useState(10)
    const [appointments, setAppointments] = useState([]);
    const [statusFilter, setStatusFilter] = useState("");
    const [totalElements, setTotalElements] = useState(0)
    const [totalPages, setTotalPages] = useState(0)
    const [arry, setArry] = useState([])
    const [msg, setMsg] = useState("")
    const [errMsg, setErrMsg] = useState("")
    const isDoctor = localStorage.getItem("isDoctor")
    const isAdmin = localStorage.getItem("isAdmin")
    const [refresh, setRefresh] = useState(true)
    const navigate = useNavigate()

    // New state to hold the ID of the appointment waiting for cancellation confirmation
    const [cancelConfirmId, setCancelConfirmId] = useState(null);

    const config = {
        headers: {
            Authorization: "Bearer " + localStorage.getItem("token")
        }
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

    useEffect(() => {

        const getAppointments = async () => {

            try {

                if (statusFilter === "") {
                    const getApi = isDoctor ?
                        `http://localhost:8080/api/appointment/doctor/all-upcoming-appointments?page=${currentPage}&size=${size}`
                        : isAdmin ?
                            `http://localhost:8080/api/appointment/admin/all-upcoming-appointments/${docid}?page=${currentPage}&size=${size}`
                            : `http://localhost:8080/api/appointment/patient/all-appointments?page=${currentPage}&size=${size}`

                    const response = await axios.get(getApi, {
                        headers: {
                            "Authorization": "Bearer " + localStorage.getItem("token")
                        },
                        params: {
                            direction: direction,
                            date: date || null,
                            word: word || null
                        }
                    })
                    console.log(response.data)
                    setTotalElements(response.data.totalElements)
                    setTotalPages(response.data.totalPages)
                    setArry(Array.from({ length: response.data.totalPages }))
                    setAppointments(response.data.appointmentList)
                }
                else {
                    const getApi = isDoctor ?
                        `http://localhost:8080/api/appointment/doctor/all-appointments-by-status?page=${currentPage}&size=${size}`
                        : isAdmin ?
                            `http://localhost:8080/api/appointment/admin/all-appointments-by-status/${docid}?page=${currentPage}&size=${size}`
                            : `http://localhost:8080/api/appointment/patient/all-appointments-by-status?page=${currentPage}&size=${size}`

                    const response = await axios.get(getApi, {
                        headers: {
                            "Authorization": "Bearer " + localStorage.getItem("token")
                        },
                        params: {
                            status: statusFilter,
                            direction: direction,
                            date: date || null,
                            word: word || null
                        }
                    })
                    console.log(response.data)
                    setTotalElements(response.data.totalElements)
                    setTotalPages(response.data.totalPages)
                    setArry(Array.from({ length: response.data.totalPages }))
                    setAppointments(response.data.appointmentList)
                }

            } catch (err) {
                console.log(err?.response?.data)
            }

        }
        getAppointments()

    }, [currentPage, statusFilter, word, date, direction, refresh])


    const initiateCancel = (id) => {
        if (!isAdmin && !isDoctor) {
            setCancelConfirmId(id); // Show confirmation toast
        } else {
            cancelFunction(id); // Admin and Doctor cancel directly
        }
    };

    const cancelFunction = async (id) => {

        const putApi = isDoctor ?
            `http://localhost:8080/api/appointment/doctor-cancel-appointment/${id}`
            : isAdmin ?
                `http://localhost:8080/api/appointment/admin-cancel-appointment/${id}/${docid}`
                : `http://localhost:8080/api/appointment/patient-cancel-appointment/${id}`

        try {
            const response = await axios.put(putApi, null, config)
            console.log(response.data)
            setErrMsg("")
            setMsg("Cancelled Successfully")
        } catch (err) {
            console.log(JSON.stringify(err))
            setMsg("")
            setErrMsg(err?.response?.data?.message)
        }

        setRefresh(!refresh)
    }

    const confirmFunction = async (id) => {
        const putConfirmApi = `http://localhost:8080/api/appointment/confirm-appointment/${id}`
        try {
            const response = await axios.put(putConfirmApi, null, config)
            console.log(response.data)
            setErrMsg("")
            setMsg("Confirmed Successfully")
        } catch (err) {
            console.log(JSON.stringify(err))
            setMsg("")
            setErrMsg(err?.response?.data?.message)
        }

        setRefresh(!refresh)
    }

    const CleanFunction = async (e) => {
        e.preventDefault()
        const putCleanApi = isDoctor ?
            `http://localhost:8080/api/appointment/doctor/past-appointments-pending-or-confirmed-cancelled`
            : `http://localhost:8080/api/appointment/admin/past-appointments-pending-or-confirmed-cancelled/${docid}`

        try {
            const response = await axios.put(putCleanApi, null, config)
            console.log(response.data)
            setErrMsg("")
            setMsg("Cleaned Up: Cancelled All Past Pending or Not Completed Appointments")
        } catch (err) {
            console.log(JSON.stringify(err))
            setMsg("")
            setErrMsg(err?.response?.data?.message)
        }

        setRefresh(!refresh)
    }

    return (

        <div
            className="dashboard-content flex-grow-1 p-4"
            style={{ minHeight: "calc(100vh - 56px)", minWidth: 0 }}
        >

            {cancelConfirmId && (
                <div>
                    <div 
                        className="position-fixed top-0 start-0 w-100 h-100" 
                        style={{ 
                            backgroundColor: "rgba(0, 0, 0, 0.5)", 
                            backdropFilter: "blur(3px)", 
                            zIndex: 1040 
                        }}
                    ></div>


                    {/* Centered Toast */}
                    <div className="position-fixed top-50 start-50 translate-middle p-3" style={{ zIndex: 1050 }}>
                        <div className="toast show align-items-center bg-white border border-warning shadow-lg rounded-4" role="alert" aria-live="assertive" aria-atomic="true">
                            <div className="toast-body text-center p-4">
                                <i className="bi bi-exclamation-triangle-fill text-warning d-block mb-3" style={{ fontSize: "3rem" }}></i>
                                <h5 className="mb-4 text-dark fw-bold">Are you sure you want to cancel this appointment?</h5>
                                <div className="d-flex justify-content-center gap-3">
                                    <button
                                        className="btn btn-danger px-4 fw-semibold"
                                        onClick={() => {
                                            cancelFunction(cancelConfirmId);
                                            setCancelConfirmId(null);
                                        }}
                                    >
                                        Yes, Cancel
                                    </button>
                                    <button
                                        className="btn btn-secondary px-4 fw-semibold"
                                        onClick={() => setCancelConfirmId(null)}
                                    >
                                        No
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )}

            {isAdmin &&
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
            }
            


            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2 className="fw-bold">
                    {(isAdmin || isDoctor) ? "Manage Appointments" : "All Appointments"}
                </h2>
            </div>

            {msg &&
                <div className="toast show align-items-center text-white bg-success border-0 w-100 mb-3" role="alert" aria-live="assertive" aria-atomic="true">
                    <div className="d-flex">
                        <div className="toast-body text-center w-100">
                            {msg}
                        </div>
                        <button type="button" className="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close" onClick={() => { setMsg("") }}></button>
                    </div>
                </div>
            }
            {errMsg &&
                <div className="toast show align-items-center text-white bg-danger border-0 w-100 mb-3" role="alert" aria-live="assertive" aria-atomic="true">
                    <div className="d-flex">
                        <div className="toast-body text-center w-100">
                            {errMsg}
                        </div>
                        <button type="button" className="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close" onClick={() => { setErrMsg("") }}></button>
                    </div>
                </div>
            }

            <div className="card shadow-sm border-0">
                <div className="card-body">

                    {/* Filters */}
                    <div className="row g-3 mb-4">

                        {/* Status */}
                        <div className="col-md-3">
                            <select
                                className="form-select"
                                value={statusFilter}
                                onChange={(e) => { setStatusFilter(e.target.value); setCurrentPage(0); }}
                            >
                                {(isAdmin || isDoctor) ?
                                    <option value="">All Upcoming</option>
                                    :
                                    <option value="">All Statuses</option>
                                }
                                <option value="PENDING">PENDING</option>
                                <option value="CONFIRMED">CONFIRMED</option>
                                <option value="COMPLETED">COMPLETED</option>
                                <option value="CANCELLED">CANCELLED</option>
                            </select>
                        </div>

                        {/* Search */}
                        <div className="col-md-3">
                            <input
                                type="text"
                                className="form-control"
                                placeholder={
                                    isDoctor || isAdmin
                                        ? "Search Patient Name"
                                        : "Search Doctor Name"
                                }
                                value={word}
                                onChange={(e) => { setWord(e.target.value); setCurrentPage(0) }}
                            />
                        </div>

                        {/* Date */}
                        <div className="col-md-2">
                            <input
                                type="date"
                                className="form-control"
                                value={date}
                                onChange={(e) => { setDate(e.target.value); setCurrentPage(0) }}
                            />
                        </div>

                        {/* Sort */}
                        <div className="col-md-2">
                            <select
                                className="form-select"
                                value={direction}
                                onChange={(e) => { setDirection(e.target.value); setCurrentPage(0) }}
                            >
                                <option value="DESC">Newest First</option>
                                <option value="ASC">Oldest First</option>
                            </select>
                        </div>

                        {/* Clean */}
                        {(isAdmin || isDoctor) &&
                            <div className="col-md-2">
                                <button
                                    className="btn text-white w-100"
                                    onClick={(e) => CleanFunction(e)}
                                    style={{
                                        backgroundColor: "#5B5FC7",
                                        borderColor: "#5B5FC7"
                                    }}
                                >
                                    <i className="bi bi-clock-history me-2"></i>
                                    Clean Up
                                </button>
                            </div>
                        }
                    </div>

                    {/* Table */}
                    <div className="table-responsive">
                        <table className="table table-hover align-middle">
                            <thead className="table-primary">
                                <tr>
                                    {
                                        (isAdmin || isDoctor) ?
                                            <th>Patient</th>
                                            :
                                            <th>Doctor</th>
                                    }
                                    <th>Date & Time</th>
                                    <th>Symptoms</th>
                                    <th>Reason</th>
                                    <th>Status</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                {appointments.map((appointment, index) => (
                                    <tr key={index}>
                                        {
                                            (isAdmin || isDoctor) ?
                                                <th>{appointment.patientName}</th>
                                                :
                                                <th>{appointment.doctorName}</th>
                                        }
                                        <td>
                                            <div>
                                                {String(appointment.date).split("-").reverse().join("-")}
                                            </div>
                                            <small className="text-muted">
                                                {convertTime(appointment.startTime)}
                                                {" - "}
                                                {convertTime(appointment.endTime)}
                                            </small>
                                        </td>
                                        <td>{appointment.symptoms}</td>
                                        <td>{appointment.reason}</td>
                                        <td>
                                            {appointment.status === "PENDING" && (
                                                <span className="badge bg-warning text-dark">
                                                    PENDING
                                                </span>
                                            )}
                                            {appointment.status === "CONFIRMED" && (
                                                <span className="badge bg-success text-white">
                                                    CONFIRMED
                                                </span>
                                            )}
                                            {appointment.status === "COMPLETED" && (
                                                <span className="badge bg-primary text-white">
                                                    COMPLETED
                                                </span>
                                            )}
                                            {appointment.status === "CANCELLED" && (
                                                <span className="badge bg-danger text-white">
                                                    CANCELLED
                                                </span>
                                            )}
                                        </td>
                                        <td>
                                            <div className="d-flex gap-2">

                                                {/* CONFIRM BUTTON (Doctor Only, Pending Only) */}
                                                {appointment.status === "PENDING" && isDoctor && (
                                                    <button
                                                        className="btn btn-light-border btn-lg" onClick={() => { confirmFunction(appointment.appointmentId) }}
                                                    >
                                                        <i className="bi bi-check-circle-fill text-success me-1"></i>
                                                    </button>
                                                )}

                                                {/* CANCEL BUTTON (Everyone if Pending, Patient ONLY if Confirmed) */}
                                                {(appointment.status === "PENDING" || (appointment.status === "CONFIRMED" && !isDoctor && !isAdmin)) && (
                                                    <button
                                                        className="btn btn-light-border btn-lg" onClick={() => { initiateCancel(appointment.appointmentId) }}
                                                    >
                                                        <i className="bi bi-x-circle-fill text-danger me-1"></i>
                                                    </button>
                                                )}

                                                {/* ADD CONSULTATION BUTTON (Doctor Only, Confirmed Only) */}
                                                {appointment.status === "CONFIRMED" && isDoctor && (
                                                    <button
                                                        className="btn btn-light-border btn-lg text-primary" onClick={() => {
                                                            navigate(`/doctor/add-consultation/${appointment.appointmentId}`)
                                                        }}
                                                    >
                                                        <i className="bi bi-heart-pulse-fill me-1"></i>
                                                    </button>
                                                )}

                                                {/* VIEW CONSULTATION BUTTON */}
                                                {appointment.status === "COMPLETED" && !isAdmin && (
                                                    <button
                                                        className="btn btn-light-border btn-lg text-primary" onClick={() => {
                                                            isDoctor ?
                                                                navigate(`/doctor/view-consultation/${appointment.appointmentId}`)
                                                                :
                                                                navigate(`/patient/view-consultation/${appointment.appointmentId}`)
                                                        }}
                                                    >
                                                        <i className="bi bi-eye me-1"></i>
                                                    </button>
                                                )}

                                            </div>
                                        </td>
                                    </tr>
                                ))}
                                {appointments.length === 0 && (
                                    <tr>
                                        <td colSpan="6" className="text-center py-4 text-muted">
                                            No appointments found.
                                        </td>
                                    </tr>
                                )}
                            </tbody>
                        </table>
                    </div>

                    {/* Pagination */}
                    <div className="d-flex flex-wrap justify-content-between align-items-center mt-4 border-top pt-3 gap-3">
                        <div className="fw-semibold text-secondary">
                            Total Appointments: {totalElements}
                        </div>

                        <div className="d-flex align-items-center gap-1">
                            <button
                                className="btn btn-outline-primary rounded-pill"
                                disabled={currentPage === 0}
                                onClick={() => { setCurrentPage(currentPage - 1) }}
                            >
                                <i className="bi bi-chevron-left"></i>
                                <span className="ms-1 d-none d-sm-inline">Previous</span>
                            </button>

                            {arry.map((_, index) => (
                                <button
                                    key={index}
                                    className={`btn rounded-pill ${currentPage === index ? 'btn-primary' : 'btn-outline-primary'}`}
                                    onClick={(e) => { setCurrentPage(index) }}
                                >
                                    {index + 1}
                                </button>
                            ))}

                            <button
                                className="btn btn-outline-primary rounded-pill"
                                disabled={totalPages === 0 || currentPage === (totalPages - 1)}
                                onClick={() => { setCurrentPage(currentPage + 1) }}
                            >
                                <span className="me-1 d-none d-sm-inline">Next</span>
                                <i className="bi bi-chevron-right"></i>
                            </button>
                        </div>

                        <div className="bg-primary text-white px-3 py-2 rounded-pill fw-bold shadow-sm">
                            Page {totalPages > 0 ? currentPage + 1 : 0} of {totalPages}
                        </div>
                    </div>

                </div>
            </div>
        </div>
    );
};

export default ManageAppointment;