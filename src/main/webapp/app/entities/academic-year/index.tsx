import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AcademicYear from './academic-year';
import AcademicYearDetail from './academic-year-detail';
import AcademicYearUpdate from './academic-year-update';
import AcademicYearDeleteDialog from './academic-year-delete-dialog';

const AcademicYearRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AcademicYear />} />
    <Route path="new" element={<AcademicYearUpdate />} />
    <Route path=":id">
      <Route index element={<AcademicYearDetail />} />
      <Route path="edit" element={<AcademicYearUpdate />} />
      <Route path="delete" element={<AcademicYearDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AcademicYearRoutes;
