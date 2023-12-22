import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import StudentPW from './student-pw';
import StudentPWDetail from './student-pw-detail';
import StudentPWUpdate from './student-pw-update';
import StudentPWDeleteDialog from './student-pw-delete-dialog';

const StudentPWRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<StudentPW />} />
    <Route path="new" element={<StudentPWUpdate />} />
    <Route path=":id">
      <Route index element={<StudentPWDetail />} />
      <Route path="edit" element={<StudentPWUpdate />} />
      <Route path="delete" element={<StudentPWDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StudentPWRoutes;
