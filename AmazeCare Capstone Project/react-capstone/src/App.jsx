
import { Route, Routes } from "react-router-dom"
import Home from "./pages/Home"
import PageNotFound from "./pages/PageNotFound"
import Auth from "./pages/Auth"
import DoctorDashboard from "./pages/DoctorDashboard"
import PatientDashboard from "./pages/PatientDashboard"
import AdminDashboard from "./pages/AdminDashboard"
import About from "./components/public/About"
import Service from "./components/public/Service"
import Contact from "./components/public/Contact"
import Register from "./components/patient/Register"
import AdminProfile from "./components/admin/AdminProfile"
import PatientProfile from "./components/patient/PatientProfile"
import DoctorProfile from "./components/doctor/DoctorProfile"
import AddAvailability from "./components/doctor/AddAvailability"
import OnboardDoctor from "./components/admin/OnboardDoctor"
import ChangePassword from "./auth/ChangePassword"
import HomeWidget from "./components/public/HomeWidget"
import DoctorWidget from "./components/doctor/DoctorWidget"
import PatientWidget from "./components/patient/PatientWidget"
import AdminWidget from "./components/admin/AdminWidget"
import UpdateDoctor from "./components/admin/UpdateDoctor"
import ManageDoctor from "./components/ManageDoctor"
import UpdateAvailability from "./components/doctor/UpdateAvailability"
import ManageAvailability from "./components/ManageAvailability"
import BookAppointment1 from "./components/patient/BookAppointment1"
import BookAppointment2 from "./components/patient/BookAppointment2"
import ManageAppointment from "./components/ManageAppointment"
import AddConsultation from "./components/doctor/AddConsultation"
import ViewConsultation from "./components/ViewConsultation"
import MedicalRecords from "./components/MedicalRecords"
import MedicalReports from "./components/MedicalReports"
import UpdateReport from "./components/patient/UpdateReport"
import AddReport from "./components/patient/AddReport"

const App = () => {

  return (
    <div>



      <Routes>
        <Route path="/login" element={<Auth />}>   </Route>



        <Route path="/" element={<Home />}>
          <Route path="" element={<HomeWidget />}></Route>
          <Route path="about" element={<About />}></Route>
          <Route path="service" element={<Service />}></Route>
          <Route path="contact" element={<Contact />}></Route>
          <Route path="register" element={<Register />}></Route>

        </Route>



        <Route path="/doctor" element={<DoctorDashboard />}>
          <Route path="" element={<DoctorWidget />}></Route>
          <Route path="add-availability" element={<AddAvailability />}> </Route>
          <Route path="update-availability" element={<UpdateAvailability />}> </Route>
          <Route path="availability" element={<ManageAvailability />}> </Route>
          <Route path="profile" element={<DoctorProfile />}> </Route>
          <Route path="change-password" element={<ChangePassword />}> </Route>
          <Route path="appointments" element={<ManageAppointment />}> </Route>
          <Route path="add-consultation/:appid" element={<AddConsultation />}> </Route>
          <Route path="view-consultation/:appid" element={<ViewConsultation />}> </Route>
          <Route path="medical-records/:appid" element={<MedicalRecords/>}> </Route>
          <Route path="medical-reports/:appid" element={<MedicalReports/>}> </Route>
          

        </Route>


        <Route path="/patient" element={<PatientDashboard />}>
          <Route path="" element={<PatientWidget />}></Route>
          <Route path="profile" element={<PatientProfile />}> </Route>
          <Route path="change-password" element={<ChangePassword />}> </Route>
         <Route path="all-doctors" element={<ManageDoctor />}> </Route>
         <Route path="availability/:docid" element={<ManageAvailability />}> </Route>
         <Route path="book-appointment1" element={<BookAppointment1 />}> </Route>
         <Route path="book-appointment2" element={<BookAppointment2 />}> </Route>
         <Route path="my-appointments" element={<ManageAppointment />}> </Route>
        <Route path="view-consultation/:appid" element={<ViewConsultation />}> </Route>
        <Route path="medical-records" element={<MedicalRecords/>}> </Route>
        <Route path="medical-reports" element={<MedicalReports/>}> </Route>
        <Route path="add-report" element={<AddReport/>}> </Route>
        <Route path="update-report/:reportid" element={<UpdateReport/>}> </Route>
        
         

        </Route>


        <Route path="/admin" element={<AdminDashboard />}>
          <Route path="" element={<AdminWidget />}></Route>
          <Route path="profile" element={<AdminProfile />}> </Route>
          <Route path="onboard-doctor" element={<OnboardDoctor />}> </Route>
          <Route path="update-doctor" element={<UpdateDoctor />}> </Route>
          <Route path="all-doctors" element={<ManageDoctor />}> </Route>
          <Route path="availability/:docid" element={<ManageAvailability />}> </Route>
          <Route path="appointments/:docid" element={<ManageAppointment />}> </Route>

        </Route>




        <Route path="*" element={<PageNotFound />}>   </Route>
      </Routes>


    </div>
  )
}
export default App