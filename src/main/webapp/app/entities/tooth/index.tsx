import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Tooth from './tooth';
import ToothDetail from './tooth-detail';
import ToothUpdate from './tooth-update';
import ToothDeleteDialog from './tooth-delete-dialog';

const ToothRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Tooth />} />
    <Route path="new" element={<ToothUpdate />} />
    <Route path=":id">
      <Route index element={<ToothDetail />} />
      <Route path="edit" element={<ToothUpdate />} />
      <Route path="delete" element={<ToothDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ToothRoutes;
