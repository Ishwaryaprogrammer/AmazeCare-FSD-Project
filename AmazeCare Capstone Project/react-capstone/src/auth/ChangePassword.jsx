import axios from "axios";
import { useState } from "react";

const ChangePassword = () => {
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [errMsgPassword, setErrMsgPassword] = useState("");
    const [errMsgconfirmPassword, setErrMsgConfirmPassword] = useState("");
    const [msg, setMsg] = useState("");
    const [errMsg, setErrMsg] = useState("");

    const config = {
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    }
    const api = "http://localhost:8080/api/auth/update"

    const update = async (e) => {
        e.preventDefault()
        
        // Reset local UI states
        setErrMsg("");
        setMsg("");
        setErrMsgPassword("");
        setErrMsgConfirmPassword("");

        if (password !== confirmPassword) {
            setErrMsg("Confirm password must be same as password")
            return
        }

        try {
            const body = { password: password }
            const resp = await axios.put(api, body, config)
            setMsg("Password changed successfully")
            setPassword("")
            setConfirmPassword("")
        } catch (err) {
            console.log(JSON.stringify(err.response))
            // Handle validation errors from backend
            setErrMsgPassword(err?.response?.data?.password || "")
            setErrMsgConfirmPassword(err?.response?.data?.confirmPassword || "")
            if (!err?.response?.data?.password && !err?.response?.data?.confirmPassword) {
                setErrMsg("An error occurred. Please try again.")
            }
        }
    }

    return (
        <div
            className="dashboard-content flex-grow-1 p-4"
            style={{ minHeight: "calc(100vh - 56px)", minWidth: 0 }}
        >
            <div className="row justify-content-center">
                <div className="col-lg-6 col-md-8">
                    <div className="card shadow-sm border-0">
                        <div className="card-body p-4">
                            <h3 className="fw-bold mb-4">Change Password</h3>
                            
                            {msg && <div className="alert alert-success">{msg}</div>}
                            {errMsg && <div className="alert alert-danger">{errMsg}</div>}

                            <form onSubmit={update}>
                                {/* New Password */}
                                <div className="mb-3">
                                    <label className="form-label fw-semibold">New Password</label>
                                    <input
                                        type="password"
                                        className={`form-control ${errMsgPassword ? 'is-invalid' : ''}`}
                                        placeholder="Enter new password" 
                                        value={password} 
                                        onChange={(e) => setPassword(e.target.value)}
                                        required
                                    />
                                    {errMsgPassword && <div className="text-danger small mt-1">{errMsgPassword}</div>}
                                </div>

                                {/* Confirm Password */}
                                <div className="mb-4">
                                    <label className="form-label fw-semibold">Confirm New Password</label>
                                    <input
                                        type="password"
                                        className={`form-control ${errMsgconfirmPassword ? 'is-invalid' : ''}`}
                                        placeholder="Confirm new password" 
                                        value={confirmPassword} 
                                        onChange={(e) => setConfirmPassword(e.target.value)}
                                        required
                                    />
                                    {errMsgconfirmPassword && <div className="text-danger small mt-1">{errMsgconfirmPassword}</div>}
                                </div>

                                <div className="d-grid">
                                    <button type="submit" className="btn btn-primary btn-lg">
                                        Update Password
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

export default ChangePassword;