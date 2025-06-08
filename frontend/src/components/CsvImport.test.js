// src/components/CsvImport.test.jsx
import { render, screen, fireEvent } from '@testing-library/react';
import CsvImport from './CsvImport';

describe('CsvImport', () => {
    const onImport = jest.fn();

    beforeEach(() => {
        onImport.mockClear();
    });

    test('shows error when no file selected', () => {
        render(<CsvImport onImport={onImport} />);
        fireEvent.click(screen.getByRole('button', { name: 'Upload CSV' }));
        expect(screen.getByText('Please select a CSV file.')).toBeInTheDocument();
        expect(onImport).not.toHaveBeenCalled();
    });

    test('calls onImport with a file', () => {
        render(<CsvImport onImport={onImport} />);
        const file = new File(['a,b\n1,2'], 'test.csv', { type: 'text/csv' });

        const input = screen.getByLabelText(/choose file/i) || screen.getByRole('textbox', { hidden: true });
        // react-testing-library >=14 can use getByTestId if you add one
        // fallback: querySelector
        const fileInput = screen.getByTestId('csv-input');

        // set files manually
        Object.defineProperty(fileInput, 'files', { value: [file] });

        fireEvent.change(fileInput);
        fireEvent.click(screen.getByRole('button', { name: 'Upload CSV' }));

        expect(onImport).toHaveBeenCalledWith(file);
    });
});
