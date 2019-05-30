import { withFormik } from "formik"
import * as yup from "yup"
import Login from "./Login"

const LoginWrapper = Login

const LoginValidation = yup.object().shape({
  email: yup
    .string()
    .email()
    .required(),
  password: yup
    .string()
    .min(8)
    .max(16)
    .matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*d)[a-zA-Zd]$")
    .required(),
})

export default withFormik({
    handleSubmit: (values, { setSubmitting }) => {
    console.log("Submitted Email:", values.email)
    console.log("Submitted Password:", values.password)
    setTimeout(() => setSubmitting(false), 3 * 1000)
  },
  validationSchema: LoginValidation,
})(LoginWrapper)