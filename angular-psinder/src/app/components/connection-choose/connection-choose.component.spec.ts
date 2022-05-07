import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConnectionChooseComponent } from './connection-choose.component';

describe('ConnectionChooseComponent', () => {
  let component: ConnectionChooseComponent;
  let fixture: ComponentFixture<ConnectionChooseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConnectionChooseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConnectionChooseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
