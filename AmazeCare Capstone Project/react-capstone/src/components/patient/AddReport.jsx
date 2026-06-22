import { useDispatch } from "react-redux";
import { add } from "../../store/action/reportAction";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const AddReport = () => {

    const navigate =useNavigate()
    const [testname, setTestName] = useState("")
    const [testDate, setTestDate] = useState("")
    const [file, setFile] = useState()
    const [msg, setMsg] = useState("")
    const [errMsg, setErrMsg] = useState("")
    const dispatch = useDispatch();

    const addFunction = (e) => {
        const formData = new FormData()
        formData.append("testName", testname)
        formData.append("date", testDate)
        formData.append("file", file)
        e.preventDefault()
        dispatch(add(formData))
        setMsg("Successfully added report")
        setTestName("")
        setTestDate("")
        setTimeout(() => {
            navigate(-1)
        }, 5000);
    }
    return (
        <div className="dashboard-content flex-grow-1 p-4">

            <div className="row justify-content-center">

                <div className="col-lg-8">

                    <div className="card shadow-sm border-0">

                        <div className="card-body p-4">

                            <h3 className="fw-bold text-center">
                                Add Medical Report
                            </h3>
                            {msg &&
                                <div className="toast show align-items-center text-white bg-success border-0 w-100 " role="alert" aria-live="assertive" aria-atomic="true">
                                    <div className="d-flex">
                                        <div className="toast-body text-center w-100">
                                            {msg}
                                        </div>
                                        <button type="button" className="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close" onClick={() => { setMsg("") }}></button>
                                    </div>
                                </div>
                            }
                            {errMsg &&
                                <div className="toast show align-items-center text-white bg-danger border-0 w-100" role="alert" aria-live="assertive" aria-atomic="true">
                                    <div className="d-flex">
                                        <div className="toast-body text-center w-100">
                                            {errMsg}
                                        </div>
                                        <button type="button" className="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close" onClick={() => { setErrMsg("") }}></button>
                                    </div>
                                </div>
                            }

                            <form onSubmit={(e) => { addFunction(e) }}>

                                <div className="mb-4">

                                    <label className="form-label fw-semibold">
                                        Test Name *
                                    </label>

                                    <input
                                        type="text" value={testname}
                                        className="form-control " onChange={(e) => { (e.target.value).trim() === "" ? setTestName("") : setTestName(e.target.value) }}
                                        placeholder="Enter test name (e.g. Blood Test, X-Ray)" required
                                    />

                                </div>

                                <div className="mb-4">

                                    <label className="form-label fw-semibold">
                                        Test Date *
                                    </label>

                                    <input
                                        type="date" value={testDate}
                                        className="form-control " onChange={(e) => { setTestDate(e.target.value) }}
                                        max={new Date().toISOString().split("T")[0]} required
                                    />

                                </div>

                                <div className="mb-4">

                                    <label className="form-label fw-semibold">
                                        Upload File *
                                    </label>

                                    <input
                                        type="file"
                                        className="form-control " onChange={(e) => {

                                            if (e.target.files[0].size < (5 * 1024 * 1024)) {
                                                setErrMsg(""); setFile(e.target.files[0])
                                            } else {
                                                e.target.value = ""
                                                setErrMsg("File size cannot be greater than 5MB")
                                            }
                                        }} required
                                    />

                                    <small className="text-muted">
                                        Allowed formats: PDF, JPG, JPEG, PNG, DOCX
                                        (Max size: 5MB)
                                    </small>

                                </div>


                                <div className="d-flex justify-content-between">

                                    <button
                                        type="button"
                                        className="btn btn-outline-secondary px-4"
                                        onClick={() => {
                                            navigate(-1)
                                        }}
                                    >
                                        Back
                                    </button>

                                    <button
                                        className="btn btn-primary"
                                    >
                                        <i className="bi bi-cloud-upload me-2"></i>
                                        Save Report
                                    </button>

                                </div>


                            </form>

                        </div>

                    </div>

                </div>

            </div>

        </div>
    )
}

export default AddReport
