import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

const ManageAvailability = () => {

    const navigate = useNavigate()
    const [availabilities, setAvailabilities] = useState([])
    const [currentPage, setCurrentPage] = useState(0)
    const [size, setSize] = useState(10)
    const [totalPages, setTotalPages] = useState(0)
    const [arry, setArry] = useState([])
    const [totalElements, setTotalElements] = useState(0)
    const isDoctor = localStorage.getItem("isDoctor")
    const [doDelete, setDoDelete] = useState(null)
    const [msg, setMsg] = useState("")

    const [day, setDay] = useState("")

    const { docid } = useParams()

    const getApi = `http://localhost:8080/api/avail/view-availabilities?page=${currentPage}&size=${size}`
    const getApiByDay = `http://localhost:8080/api/avail/view-availabilities-by-day?page=${currentPage}&size=${size}`


    const config = {
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    }
    const config2 = {
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        params: {
            day: day
        }
    }
    
    const DeleteAvailability = async (availabilityId) => {
        try {
            const response = await axios.delete(`http://localhost:8080/api/avail/delete/${availabilityId}`, config)
            console.log(response)
            setAvailabilities([...availabilities].filter((i) => i.id !== availabilityId))
            setMsg("Deleted Successfully")
        } catch (err) {
            console.log(JSON.stringify(err))
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
        const getAvailablities = async () => {
            try {
                let response;
                if (isDoctor) {
                    response = (day === "") ? await axios.get(getApi, config) : await axios.get(getApiByDay, config2)
                } else {
                    if (day === "") {
                        response = await axios.get(`http://localhost:8080/api/avail/view-availabilities/${docid}?page=${currentPage}&size=${size}`, config)
                    } else {
                        response = await axios.get(`http://localhost:8080/api/avail/view-availabilities-by-day/${docid}?page=${currentPage}&size=${size}`, config2)
                    }
                }
                console.log(response.data)
                setTotalElements(response.data.totalElements)
                setTotalPages(response.data.totalPages)
                setArry(Array.from({ length: response.data.totalPages }))
                setAvailabilities(response.data.list)

            } catch (err) {
                console.log(JSON.stringify(err))
            }
        }

        getAvailablities()

    }, [currentPage, day])

    return (

        <div
            className="dashboard-content flex-grow-1 p-4"
            style={{ minHeight: "calc(100vh - 56px)", minWidth: 0 }}
        >
            {/* Added Back Button for Admins and Patients navigating to this page */}
            {!isDoctor && (
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
            )}

            <div className="d-flex flex-wrap justify-content-between align-items-center mb-4 gap-3">
                <h2 className="fw-bold mb-0">
                    Availability List
                </h2>

                {isDoctor && (
                    <button
                        className="btn btn-primary"
                        onClick={() => navigate("/doctor/add-availability")}
                    >
                        <i className="bi bi-calendar-plus me-2"></i>
                        Add Availability
                    </button>
                )}
            </div>

            {/* Success Message Alert */}
            {msg && (
                <div className="alert alert-success shadow-sm border-0 d-flex align-items-center mb-4" role="alert">
                    <i className="bi bi-check-circle-fill fs-5 me-3 text-success"></i>
                    <div className="flex-grow-1 fw-semibold">{msg}</div>
                    <button type="button" className="btn-close" onClick={() => { setMsg("") }}></button>
                </div>
            )}

            {/* Confirmation Toast Overlay with Backdrop */}
            {doDelete && (
                <>
                    {/* Dimmed Background Overlay */}
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
                                <h5 className="mb-4 text-dark fw-bold">Are you sure you want to delete this availability?</h5>
                                <div className="d-flex justify-content-center gap-3">
                                    <button
                                        className="btn btn-danger px-4 fw-semibold"
                                        onClick={() => {
                                            DeleteAvailability(doDelete);
                                            setDoDelete(null);
                                        }}
                                    >
                                        Yes, Delete
                                    </button>
                                    <button
                                        className="btn btn-secondary px-4 fw-semibold"
                                        onClick={() => setDoDelete(null)}
                                    >
                                        No
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </>
            )}

            <div className="card shadow-sm border-0">
                <div className="card-body">

                    <div className="mb-4">
                        <select
                            className="form-select fw-semibold"
                            style={{ maxWidth: "250px", width: "100%" }}
                            value={day}
                            onChange={(e) => {
                                setDay(e.target.value);
                                setCurrentPage(0);
                            }}
                        >
                            <option value="">All Days</option>
                            <option value="MONDAY">MONDAY</option>
                            <option value="TUESDAY">TUESDAY</option>
                            <option value="WEDNESDAY">WEDNESDAY</option>
                            <option value="THURSDAY">THURSDAY</option>
                            <option value="FRIDAY">FRIDAY</option>
                            <option value="SATURDAY">SATURDAY</option>
                            <option value="SUNDAY">SUNDAY</option>
                        </select>
                    </div>

                    <div className="table-responsive">
                        <table className="table table-hover align-middle">
                            <thead className="table-primary">
                                <tr>
                                    <th>Day</th>
                                    <th>Start Time</th>
                                    <th>End Time</th>
                                    <th>Duration (Min)</th>
                                    {isDoctor && <th>Actions</th>}
                                </tr>
                            </thead>
                            <tbody>
                                {availabilities.map((availability, index) => (
                                    <tr key={index}>
                                        <td className="fw-semibold">{availability.day}</td>
                                        <td>{convertTime(availability.startTime)}</td>
                                        <td>{convertTime(availability.endTime)}</td>
                                        <td>{availability.duration}</td>
                                        {isDoctor && (
                                            <td>
                                                <div className="d-flex gap-2 flex-wrap">
                                                    <button
                                                        className="btn btn-light-border btn-lg"
                                                        onClick={() =>
                                                            navigate("/doctor/update-availability", { state: availability })
                                                        }
                                                    >
                                                        <i className="bi bi-pencil-square text-warning me-1"></i>
                                                    </button>
                                                    <button
                                                        className="btn btn-light-border btn-lg"
                                                        onClick={() => { setDoDelete(availability.id) }}
                                                    >
                                                        <i className="bi bi-trash text-danger me-1"></i>
                                                    </button>
                                                </div>
                                            </td>
                                        )}
                                    </tr>
                                ))}

                                {/* Empty State Handling */}
                                {availabilities.length === 0 && (
                                    <tr>
                                        <td colSpan={isDoctor ? "5" : "4"} className="text-center py-4 text-muted">
                                            No availabilities found.
                                        </td>
                                    </tr>
                                )}
                            </tbody>
                        </table>
                    </div>

                    {/* Pagination */}
                    <div className="d-flex flex-wrap justify-content-between align-items-center mt-4 border-top pt-3 gap-3">
                        <div className="fw-semibold text-secondary">
                            Total Availabilities: {totalElements}
                        </div>

                        <div className="d-flex align-items-center gap-1">
                            <button
                                className="btn btn-outline-primary rounded-pill"
                                onClick={() => { setCurrentPage(currentPage - 1); }}
                                disabled={currentPage === 0}
                            >
                                <i className="bi bi-chevron-left"></i>
                                <span className="ms-1 d-none d-sm-inline">Previous</span>
                            </button>

                            {arry.map((_, index) => (
                                <button 
                                    key={index}
                                    className={`btn rounded-pill ${currentPage === index ? 'btn-primary' : 'btn-outline-primary'}`}
                                    onClick={() => { setCurrentPage(index) }}
                                >
                                    {index + 1}
                                </button>
                            ))}

                            <button
                                className="btn btn-outline-primary rounded-pill"
                                onClick={() => { setCurrentPage(currentPage + 1); }}
                                disabled={totalPages === 0 || currentPage === totalPages - 1}
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

export default ManageAvailability;