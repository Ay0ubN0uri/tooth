/* eslint-disable @typescript-eslint/no-base-to-string */
import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import axios from 'axios';

const initialState = {
  loading: true,
  students: [],
  pws: [],
};

export type MyStudentsState = Readonly<typeof initialState>;

const apiUrl = 'api/students/mystudents';

export const getMyStudents = createAsyncThunk(
  'mystudents/get_my_students',
  async ({ academicYear, group }: { academicYear: any; group: any }) => axios.get<any>(`${apiUrl}/${academicYear}/${group}`),
  {
    serializeError: serializeAxiosError,
  },
);

export const getPWsByStudentId = createAsyncThunk(
  'mystudents/get_pws_by_student_id',
  async ({ studentId }: { studentId: any }) => axios.get<any>(`api/students/${studentId}/pws`),
  {
    serializeError: serializeAxiosError,
  },
);

export const MyStudentsSlice = createSlice({
  name: 'mystudents',
  initialState: initialState as MyStudentsState,
  reducers: {},
  extraReducers(builder) {
    builder
      .addCase(getMyStudents.pending, state => {
        state.loading = true;
      })
      .addCase(getMyStudents.fulfilled, (state, action) => {
        return {
          ...state,
          loading: false,
          students: action.payload.data,
        };
      })
      .addCase(getPWsByStudentId.pending, state => {
        state.loading = true;
      })
      .addCase(getPWsByStudentId.fulfilled, (state, action) => {
        return {
          ...state,
          loading: false,
          pws: action.payload.data,
        };
      });
  },
});

// Reducer
export default MyStudentsSlice.reducer;
