import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';
import React from 'react';
import { Route } from 'react-router';
import MyStudents from './my-students';

const MyStudentsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MyStudents />} />
  </ErrorBoundaryRoutes>
);

export default MyStudentsRoutes;
